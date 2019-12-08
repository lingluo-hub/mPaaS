package com.ibyte.sys.attach.dto.config;

import com.ibyte.sys.attach.dto.SysAttachCatalogVO;
import lombok.Getter;
import lombok.Setter;

/**
 * 附件机制系统配置对象
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Getter
@Setter
public class SysAttachSystemVO {

    private String storeLocation;

    private String encryptMethod;

    private SysAttachAliOssConfigVO aliOssConfig;

    private SysAttachCatalogVO catalog;


}