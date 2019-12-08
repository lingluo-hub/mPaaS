package com.ibyte.framework.discovery.client;

import feign.MethodMetadata;
import feign.Util;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

import static feign.Util.checkState;
import static feign.Util.emptyToNull;
import static org.springframework.core.annotation.AnnotationUtils.synthesizeAnnotation;

/**
 * 自定义Feign Contract，支持接口多层继承
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Component
public class FeignHierarchicalContract extends SpringMvcContract {
    private static final String PATH_SPLIT = "/";

    private ResourceLoader resourceLoader;

    @Override
    public List<MethodMetadata> parseAndValidatateMetadata(final Class<?> targetType) {
        checkState(targetType.getTypeParameters().length == 0,
                "Parameterized types unsupported: %s",
                targetType.getSimpleName());
        final Map<String, MethodMetadata> result = new LinkedHashMap<>();
        for (final Method method : targetType.getMethods()) {
            if (ignoreMethod(method)) {
                continue;
            }
            final MethodMetadata metadata = this.parseAndValidateMetadata(targetType, method);
            checkState(!result.containsKey(metadata.configKey()), "Overrides unsupported: %s", metadata.configKey());
            result.put(metadata.configKey(), metadata);
        }
        return new ArrayList<>(result.values());
    }

    /**
     * 忽略的方法
     * @param method
     * @return
     */
    private boolean ignoreMethod(Method method) {
        return method.getDeclaringClass() == Object.class || (method.getModifiers() & Modifier.STATIC) != 0
                || Util.isDefault(method) || (AnnotationUtils.findAnnotation(method, RequestMapping.class)==null);
    }

    @Override
    public MethodMetadata parseAndValidateMetadata(final Class<?> targetType, final Method method) {
        final MethodMetadata methodMetadata = super.parseAndValidateMetadata(targetType, method);

        final LinkedList<Class<?>> classHierarchy = new LinkedList<>();
        // 找到有这个方法的所有接口类
        classHierarchy.add(targetType);
        this.findClass(targetType, method.getDeclaringClass(), classHierarchy);
        // 给请求增加根路径，根据接口类上的RequestMapping注解
        classHierarchy.stream()
                .map(this::processPathValue)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .ifPresent((path) -> methodMetadata.template().insert(0, path));
        return methodMetadata;
    }

    /**
     * 获取类上的RequestMapping注解的第一个path
     * @param clz
     * @return
     */
    private Optional<String> processPathValue(final Class<?> clz) {
        Optional<String> result = Optional.empty();

        final RequestMapping classAnnotation = clz.getAnnotation(RequestMapping.class);
        if (classAnnotation != null) {
            final RequestMapping synthesizeAnnotation = synthesizeAnnotation(classAnnotation, clz);
            // Prepend path from class annotation if specified
            if (synthesizeAnnotation.value().length > 0) {
                String pathValue = emptyToNull(synthesizeAnnotation.value()[0]);
                pathValue = this.resolveValue(pathValue);
                if (!pathValue.startsWith(PATH_SPLIT)) {
                    pathValue = PATH_SPLIT + pathValue;
                }
                result = Optional.of(pathValue);
            }
        }
        return result;
    }

    private String resolveValue(final String value) {
        if (StringUtils.hasText(value) && this.resourceLoader instanceof ConfigurableApplicationContext) {
            return ((ConfigurableApplicationContext) this.resourceLoader).getEnvironment().resolvePlaceholders(value);
        }
        return value;
    }

    @Override
    protected void processAnnotationOnClass(final MethodMetadata data, final Class<?> clz) {
        // skip this step
    }

    /**
     * 找到从currentClass到searchClass之间的继承类，不包含currentClass，包含searchClass
     * @param currentClass 当前接口
     * @param searchClass 查找的父接口
     * @param classHierarchy 保存的集合
     * @return
     */
    private boolean findClass(final Class<?> currentClass, final Class<?> searchClass,
                              final LinkedList<Class<?>> classHierarchy) {
        if (currentClass == searchClass) {
            return true;
        }
        final Class<?>[] interfaces = currentClass.getInterfaces();
        for (final Class<?> currentInterface : interfaces) {
            classHierarchy.add(currentInterface);
            final boolean findClass = this.findClass(currentInterface, searchClass, classHierarchy);
            if (findClass) {
                return true;
            }
            classHierarchy.removeLast();
        }
        return false;
    }

    @Override
    public void setResourceLoader(final ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        super.setResourceLoader(resourceLoader);
    }
}
