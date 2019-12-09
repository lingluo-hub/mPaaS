package com.ibyte.sys.attach.meta;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ibyte.framework.plugin.annotation.BaseOnProperty;
import com.ibyte.framework.serializer.InterfaceProxy;
import com.ibyte.sys.attach.support.store.ISysAttachStore;
import com.ibyte.sys.attach.support.store.ISysAttachStoreDirect;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 附件存储扩展provider/config
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Setter
@Getter
@ToString
public class SysAttachStore {

    /**
     * 存储系统标识，存储于file表的location字段中
     */
    private String id;

    /**
     * 存储系统名称(如：文件系统，OSS等)
     */
    private String messageKey;

    /**
     * 附件存储扩展接口的实现
     */
    @BaseOnProperty
    private ISysAttachStore sysAttachStore;

    /**
     * 直连service的实现
     */
    @JsonSerialize(using = InterfaceProxy.Serializer.class)
    @JsonDeserialize(using = InterfaceProxy.Deserializer.class)
    private ISysAttachStoreDirect sysAttachStoreDirect;

}
