package com.ibyte.sys.attach.store;

import com.ibyte.sys.attach.support.store.ISysAttachStoreProxy;

import java.io.InputStream;

/**
 * 抽象个代理商对象写入-子类继承实现
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
public abstract class AbstractSysAttachStoreProxy implements ISysAttachStoreProxy {

    @Override
    public void writeFile(InputStream inputSream, String filePath) {
        writeFile(inputSream, filePath, null);
    }
}
