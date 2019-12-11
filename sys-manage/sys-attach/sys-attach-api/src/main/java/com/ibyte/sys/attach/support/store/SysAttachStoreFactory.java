package com.ibyte.sys.attach.support.store;

import com.ibyte.framework.discovery.api.ISystemConfigApi;
import com.ibyte.framework.discovery.dto.SystemConfigVo;
import com.ibyte.framework.plugin.Plugin;
import com.ibyte.sys.attach.config.SysAttachConfig;
import com.ibyte.sys.attach.constant.AttachLocationEnum;
import com.ibyte.sys.attach.constant.SysAttachConstant;
import com.ibyte.sys.attach.meta.SysAttachStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 附件存储service工厂
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Component
public class SysAttachStoreFactory {

    @Autowired
    private ISystemConfigApi configApi;

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

    /**
     * 获取默认的附件存储扩展点id
     */
    public String getDefaultLocation() {
        SystemConfigVo attachConfig  = configApi.findOne(SysAttachConstant.SYS_ATTACH_DEFAULT_STORE);
        if(attachConfig == null) {//默认使用本地文件存储
            attachConfig = new SystemConfigVo();
            attachConfig.setFdKey(SysAttachConstant.SYS_ATTACH_DEFAULT_STORE);
            attachConfig.setFdValue(AttachLocationEnum.SERVER.getValue());
            List<SystemConfigVo> configList = new ArrayList<>();
            configList.add(attachConfig);
            configApi.saveAll(configList);
        }
        return attachConfig.getFdValue();
    }
}
