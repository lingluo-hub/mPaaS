package com.ibyte.sys.attach.config;

import com.ibyte.framework.discovery.api.ISystemConfigApi;
import com.ibyte.framework.discovery.dto.SystemConfigVo;
import com.ibyte.sys.attach.constant.SysAttachConstant;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 文件配置
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Component
@Getter
public class SysAttachConfig{

    @Autowired
    ISystemConfigApi configApi;

    /**
     * 当前的存储实现(附件存储扩展点id)
     */
    @Value("${sys.attach.store.currentStoreLocation:server}")
    private String currentStoreLocation;

    /**
     * 当前的加解密实现(加解密扩展点id)
     */
    @Value("${sys.attach.store.encry.currentEncryMethod:NONE}")
    private String currentEncryMethod;

    public void refreshConfig() {
        SystemConfigVo locationConfig = configApi.findOne(SysAttachConstant.SYS_ATTACH_DEFAULT_STORE);
        if (locationConfig != null) {
            currentStoreLocation = locationConfig.getFdValue();
        }

        SystemConfigVo encryptConfig = configApi.findOne(SysAttachConstant.SYS_ATTACH_DEFAULT_ENCRY);
        if (encryptConfig != null) {
            currentEncryMethod = encryptConfig.getFdValue();
        }
    }
}
