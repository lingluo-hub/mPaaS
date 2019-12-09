package com.ibyte.attach.core.api.store;

import com.ibyte.framework.plugin.Plugin;
import com.ibyte.sys.attach.config.SysAttachConfig;
import com.ibyte.sys.attach.meta.SysAttachStore;
import com.ibyte.sys.attach.support.store.ISysAttachStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 附件存储service工厂
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Component
public class SysAttachStoreFactory {

    @Autowired
    private SysAttachConfig sysAttachConfig;

    @Autowired
    private SysAttachStoreFactory sysAttachStoreFactory;

    /**
     * 获取指定location的附件存储扩展点（SysAttachStore注解，一般用于读文件）
     */
    public ISysAttachStore getAttachStoreService(String location) {
        SysAttachStore storeProvider = Plugin.getProvider(SysAttachStore.class, location);
        return storeProvider.getSysAttachStore();
    }

    /**
     * 获取默认的附件存储扩展点（SysAttachStore注解，一般用于写文件）
     */
    public ISysAttachStore getAttachStoreService() {
        return sysAttachStoreFactory.getAttachStoreService(sysAttachConfig.getCurrentStoreLocation());
    }
}
