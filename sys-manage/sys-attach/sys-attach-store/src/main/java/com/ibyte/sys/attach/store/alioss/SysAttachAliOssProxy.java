package com.ibyte.sys.attach.store.alioss;

import com.ibyte.sys.attach.store.AbstractSysAttachStoreProxy;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;

/**
 * 阿里云对象存储流代理实现
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Service
@Aspect
public class SysAttachAliOssProxy extends AbstractSysAttachStoreProxy {

    @Override
    public void writeFile(InputStream inputSream, String filePath, Map<String, String> header) {

    }

    @Override
    public String buildFullPath(String catalog, String modelPath, String filePath) {
        return null;
    }

    @Override
    public InputStream readFile(String filePath, String encryMethod) {
        return null;
    }
}
