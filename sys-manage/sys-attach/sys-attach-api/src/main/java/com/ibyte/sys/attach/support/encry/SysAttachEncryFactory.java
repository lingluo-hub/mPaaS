package com.ibyte.sys.attach.support.encry;

import com.ibyte.framework.discovery.api.ISystemConfigApi;
import com.ibyte.framework.discovery.dto.SystemConfigVo;
import com.ibyte.framework.plugin.Extension;
import com.ibyte.framework.plugin.Plugin;
import com.ibyte.sys.attach.annotation.SysAttachEncry;
import com.ibyte.sys.attach.constant.AttachEncryMethodEnum;
import com.ibyte.sys.attach.constant.SysAttachConstant;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 附件加密service工厂
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
public class SysAttachEncryFactory {

    @Autowired
    ISystemConfigApi configApi;

    /**
     * 获取默认的附件加解密扩展点（SysAttachEncry注解，一般用于加密文件）
     */
    public ISysAttachEncry getAttachEncryService() {
        SystemConfigVo attachConfig = configApi.findOne(SysAttachConstant.SYS_ATTACH_DEFAULT_ENCRY);
        if (attachConfig == null) {//默认不加密
            attachConfig = new SystemConfigVo();
            attachConfig.setFdKey(SysAttachConstant.SYS_ATTACH_DEFAULT_ENCRY);
            attachConfig.setFdValue(AttachEncryMethodEnum.NONE.getValue());
            List<SystemConfigVo> configList = new ArrayList<>();
            configList.add(attachConfig);
            configApi.saveAll(configList);
        }
        Extension extension = Plugin.getExtension(SysAttachEncry.class, attachConfig.getFdValue());
        return extension.getProvider();
    }

    /**
     * 获取指定加密方式的附件加解密扩展点（SysAttachEncry注解，一般用于解密文件）
     */
    public ISysAttachEncry getAttachEncryService(String encryMethod) {
        Extension extension = Plugin.getExtension(SysAttachEncry.class, encryMethod);
        return extension.getProvider();
    }

}
