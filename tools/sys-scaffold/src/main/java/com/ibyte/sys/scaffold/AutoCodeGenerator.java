package com.ibyte.sys.scaffold;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
public class AutoCodeGenerator {
    /**
     * 标准包下扩展的包
     */
    private String extPackage;
    /**
     * 模块根路径
     */
    private String basePath;
    /**
     * 模块名称：
     */
    private String moduleName;
    /**
     * 实体名，
     */
    private String entityName;
    /**
     * 作者
     */
    private String author;
    /**
     * 注释
     */
    private String comment;
    /**
     * 忽略生成controller
     */
    private boolean ignoreController;
    /**
     * sys/portal
     */
    private String modulePath;
    /**
     * sys.portal
     */
    private String moduleDot;
    /**
     * src/main/java/com/ibyte/sys/
     */
    private String javaCommonPath;

    private String apiName;

    private String repositoryName;

    private String serviceName;

    private String dtoName;

    private String controllerName;

    private final String coreKey = "core";


    private AutoCodeGenerator(String basePath, String moduleName, String entityName, String extPackage,
                              String author, String comment, boolean ignoreController) {
        this.basePath = basePath;
        this.moduleName = moduleName;
        this.entityName = entityName;
        this.apiName = "I" + entityName + "Api";
        this.repositoryName = "I" + entityName + "Repository";
        this.serviceName = entityName + "Service";
        this.dtoName = entityName + "VO";
        this.controllerName = entityName + "Controller";
        this.modulePath = moduleName.replace("-", "/");
        this.moduleDot = moduleName.replace("-", ".");
        this.javaCommonPath = "/src/main/java/com/ibyte/" + this.modulePath;
        this.extPackage = extPackage;
        this.author = author;
        this.comment = comment;
        this.ignoreController = ignoreController;
    }

    public void generate() {
        writeApi();
        writeDto();
        writeEntity();
        writeRepository();
        writeService();
        if (!this.ignoreController) {
            writeController();
        }
    }

    private void writeService() {
        String path = createDirFile("core", "service");
        try (BufferedWriter serviceInput = new BufferedWriter(new FileWriter(getJavaFilePath(path, this.serviceName)))) {
            serviceInput.write(getPackageLine("service", "core") + "\r\n");
            serviceInput.newLine();
            serviceInput.write(importPrefix("com.ibyte.common.core.service.AbstractServiceImpl;") + "\r\n");
            serviceInput.write(getImportLine("api", this.apiName, "api") + "\r\n");
            serviceInput.write(getImportLine("dto", this.dtoName, "api") + "\r\n");
            serviceInput.write(getImportLine("entity", this.entityName, "core") + "\r\n");
            serviceInput.write(getImportLine("repository", this.repositoryName, "core") + "\r\n");
            serviceInput.write(importPrefix("org.springframework.stereotype.Service;") + "\r\n");
            serviceInput.write(importPrefix("org.springframework.web.bind.annotation.RequestMapping;") + "\r\n");
            serviceInput.write(importPrefix("org.springframework.web.bind.annotation.RestController;") + "\r\n");
            serviceInput.write(importPrefix("org.springframework.transaction.annotation.Transactional;") + "\r\n");
            serviceInput.newLine();
            serviceInput.write(buildComments());
            serviceInput.write("@Service\r\n");
            serviceInput.write("@RestController\r\n");
            serviceInput.write(baseRequestMapping("api") + "\r\n");
            serviceInput.write("@Transactional(readOnly = true,rollbackFor = {Throwable.class})\r\n");
            serviceInput.write(String.format("public class %s extends AbstractServiceImpl<%s,%s,%s> implements %s {",
                    this.serviceName, this.repositoryName, this.entityName, this.dtoName, this.apiName) + "\r\n");
            serviceInput.newLine();
            serviceInput.write("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeRepository() {
        String path = createDirFile("core", "repository");
        try (BufferedWriter repositoryInput = new BufferedWriter(new FileWriter(getJavaFilePath(path, this.repositoryName)))) {
            repositoryInput.write(getPackageLine("repository", "core") + "\r\n");
            repositoryInput.newLine();
            repositoryInput.write(importPrefix("com.ibyte.common.core.repository.IRepository;") + "\r\n");
            repositoryInput.write(getImportLine("entity", this.entityName, "core") + "\r\n");
            repositoryInput.newLine();
            repositoryInput.write(importPrefix("org.springframework.stereotype.Repository;") + "\r\n");
            repositoryInput.newLine();
            repositoryInput.write(buildComments());
            repositoryInput.write("@Repository\r\n");
            repositoryInput.write(String.format("public interface %s extends IRepository<%s> {", this.repositoryName, this.entityName) + "\r\n");
            repositoryInput.newLine();
            repositoryInput.write("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeEntity() {
        String path = createDirFile("core", "entity");
        try (BufferedWriter entityInput = new BufferedWriter(new FileWriter(getJavaFilePath(path, this.entityName)))) {
            entityInput.write(getPackageLine("entity", "core") + "\r\n");
            entityInput.newLine();
            entityInput.write(importPrefix("com.ibyte.common.core.entity.AbstractEntity;") + "\r\n");
            entityInput.write(importPrefix("lombok.Getter;") + "\r\n");
            entityInput.write(importPrefix("lombok.Setter;") + "\r\n");
            entityInput.newLine();
            entityInput.write(importPrefix("javax.persistence.Entity;") + "\r\n");
            entityInput.write(importPrefix("javax.persistence.Table;") + "\r\n");
            entityInput.newLine();
            entityInput.write(buildComments());
            entityInput.write("@Getter\r\n");
            entityInput.write("@Setter\r\n");
            entityInput.write("@Entity\r\n");
            entityInput.write("@Table\r\n");
            entityInput.write(String.format("public class %s extends AbstractEntity {", this.entityName) + "\r\n");
            entityInput.newLine();
            entityInput.write("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeController() {
        String path = createDirFile("core", "controller");
        try (BufferedWriter controllerInput = new BufferedWriter(new FileWriter(getJavaFilePath(path, this.controllerName)))) {
            controllerInput.write(getPackageLine("controller", "core") + "\r\n");
            controllerInput.newLine();
            controllerInput.write(importPrefix("com.ibyte.common.core.controller.AbstractController;") + "\r\n");
            controllerInput.write(getImportLine("api", this.apiName, "api") + "\r\n");
            controllerInput.write(getImportLine("dto", this.dtoName, "api") + "\r\n");
            controllerInput.write(importPrefix("org.springframework.web.bind.annotation.RequestMapping;") + "\r\n");
            controllerInput.write(importPrefix("org.springframework.web.bind.annotation.RestController;") + "\r\n");
            controllerInput.newLine();
            controllerInput.write(buildComments());
            controllerInput.write("@RestController\r\n");
            controllerInput.write(baseRequestMapping("data") + "\r\n");
            controllerInput.write(String.format("public class %s extends AbstractController<%s,%s> {",
                    this.controllerName, this.apiName, this.dtoName) + "\r\n");
            controllerInput.newLine();
            controllerInput.write("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeDto() {
        String path = createDirFile("api", "dto");
        try (BufferedWriter dtoInput = new BufferedWriter(new FileWriter(getJavaFilePath(path, this.dtoName)))) {
            dtoInput.write(getPackageLine("dto", "api") + "\r\n");
            dtoInput.newLine();
            dtoInput.write(importPrefix("com.ibyte.common.core.dto.AbstractVO;") + "\r\n");
            dtoInput.write(importPrefix("lombok.Getter;") + "\r\n");
            dtoInput.write(importPrefix("lombok.Setter;") + "\r\n");
            dtoInput.write(importPrefix("lombok.ToString;") + "\r\n");
            dtoInput.newLine();
            dtoInput.write(buildComments());
            dtoInput.write("@Getter\r\n");
            dtoInput.write("@Setter\r\n");
            dtoInput.write("@ToString\r\n");
            dtoInput.write(String.format("public class %s extends AbstractVO {", this.dtoName) + "\r\n");
            dtoInput.newLine();
            dtoInput.write("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeApi() {
        String path = createDirFile("api", "api");
        try (BufferedWriter apiInput = new BufferedWriter(new FileWriter(getJavaFilePath(path, this.apiName)))) {
            apiInput.write(getPackageLine("api", "api") + "\r\n");
            apiInput.newLine();
            apiInput.write(importPrefix("com.ibyte.common.core.api.IApi;") + "\r\n");
            apiInput.write(getImportLine("dto", this.dtoName, "api") + "\r\n");
            apiInput.newLine();
            apiInput.write(buildComments());
            apiInput.write(String.format("public interface %s extends IApi<%s> {", this.apiName, this.dtoName) + "\r\n");
            apiInput.newLine();
            apiInput.write("}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 首字母小写
     * @param s
     * @return
     */
    private String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0)))
                    .append(s.substring(1)).toString();
        }
    }

    private String baseRequestMapping(String type) {
        return String.format("@RequestMapping(\"/%s/%s/%s\")", type, this.moduleName,
                toLowerCaseFirstOne(this.entityName));
    }

    /**
     * basePath/sys-portal-api/javaCommonPath/pkg
     * 这里已经处理了扩展包路径
     * @param fileSuffix api或是core或是client
     * @param pkg
     * @return
     */
    private String createDirFile(String fileSuffix, String pkg) {
        String coreModuleKey = "core";
        if (coreModuleKey.equals(fileSuffix)) {
            pkg = this.coreKey + "/" + pkg;
        }
        String path = String.format("%s/%s-%s%s/%s", this.basePath, this.moduleName,
                fileSuffix, this.javaCommonPath, pkg);
        if (!StringUtils.isEmpty(this.extPackage)) {
            path += "/" + this.extPackage.replaceAll("\\.", "/");
        }
        path = formatPath(path);
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return path;
    }

    /**
     * java文件路径，这里不需要处理扩展包路径
     * @param path 已处理extPackage
     * @param javaFile
     * @return
     */
    private String getJavaFilePath(String path, String javaFile) {
        return path + "/" + javaFile + ".java";
    }

    private String formatPath(String path) {
        path = path.replace("\\", "/");
        path = path.replace("//", "/");
        return path;
    }

    /**
     * package com.ibyte.sys.portal.api
     * @param name 包名，如：api entity dto
     * @param moduleName  模块名，如：api client core
     * @return
     */
    private String getPackageLine(String name, String moduleName) {
        String coreModuleKey = "core";
        if (coreModuleKey.equals(moduleName)) {
            name = this.coreKey + "." + name;
        }
        if (StringUtils.isEmpty(this.extPackage)) {
            return String.format("package com.ibyte.%s.%s;", this.moduleDot, name);
        } else {
            return String.format("package com.ibyte.%s.%s.%s;", this.moduleDot, name, this.extPackage);
        }
    }

    /**
     * import com.ibyte.sys.portal.api.ISysPortalThemeTplApi
     * @param name 包名，如 entity service dto
     * @param className 类名，如：ISysPortalPortalDraftApi
     * @param moduleName 所属模块名，如 api core client
     * @return
     */
    private String getImportLine(String name, String className, String moduleName) {
        String coreModuleKey = "core";
        if (coreModuleKey.equals(moduleName)) {
            name = this.coreKey + "." + name;
        }
        if (StringUtils.isEmpty(this.extPackage)) {
            return importPrefix(String.format("com.ibyte.%s.%s.%s;", this.moduleDot, name, className));
        } else {
            return importPrefix(String.format("com.ibyte.%s.%s.%s.%s;", this.moduleDot, name, this.extPackage, className));
        }
    }

    private String importPrefix(String importStr) {
        return "import " + importStr;
    }

    /**
     * 设置注释
     * @return
     */
    /**
     * 门户主题类型
     * @author: zhengchao
     * @date: 2018/11/28
     */
    private String buildComments() {
        StringBuffer comments = new StringBuffer();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String comment = StringUtils.isEmpty(this.comment) ? "" : this.comment;
        String author = StringUtils.isEmpty(this.author) ? "" : this.author;
        comments.append("/** \r\n")
                .append(" * " + comment + "\r\n")
                .append(" * \r\n")
                .append(" * @author " + author + "\r\n")
                .append(" * @since 1.0.1")
                .append(" */\r\n");
        return comments.toString();
    }

    public static Builder newBuilder(final String basePath, final String moduleName, final String entityName) {
        return new Builder(basePath, moduleName, entityName);
    }

    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Builder {

        private final String basePath;

        private final String moduleName;

        private final String entityName;

        private String author;

        private String comment;

        private String extPackage;

        private boolean ignoreController;

        public Builder extPackage(final String extPackage) {
            this.extPackage = extPackage;
            return this;
        }

        public Builder author(final String author) {
            this.author = author;
            return this;
        }

        public Builder comment(final String comment) {
            this.comment = comment;
            return this;
        }

        public Builder ignoreController(final boolean ignoreController) {
            this.ignoreController = ignoreController;
            return this;
        }

        public final AutoCodeGenerator build() {
            return new AutoCodeGenerator(basePath, moduleName, entityName, extPackage, author, comment, ignoreController);
        }
    }

    public static void main(String[] args) {
        String path = "E:\\M-Pass";
        AutoCodeGenerator generator = AutoCodeGenerator.newBuilder(
                path,
                "sys-attach",
                "xxxxxxx")
                .author("<a href=\"mailto:shangzhi.ibyte@gmail.com\">iByte</a>").comment("xxxxx")
                .ignoreController(false).build();
        generator.generate();
        System.out.println("成功");
    }
}
