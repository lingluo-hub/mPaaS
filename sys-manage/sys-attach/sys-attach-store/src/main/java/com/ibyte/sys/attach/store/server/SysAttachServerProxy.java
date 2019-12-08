package com.ibyte.sys.attach.store.server;

import com.ibyte.sys.attach.store.AbstractSysAttachStoreProxy;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;

/**
 * 文件本地存储流代理实现
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Service
public class SysAttachServerProxy extends AbstractSysAttachStoreProxy {

    @Override
    public void writeFile(InputStream inputSream, String filePath, Map<String, String> header) {

    }
}
