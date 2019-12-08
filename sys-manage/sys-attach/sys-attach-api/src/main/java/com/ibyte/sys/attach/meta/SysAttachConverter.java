package com.ibyte.sys.attach.meta;

import com.ibyte.framework.plugin.annotation.BaseOnProperty;
import com.ibyte.sys.attach.support.convert.ISysAttachConverter;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Setter
@Getter
@ToString
public class SysAttachConverter {

    /**
     * 附件转换器标识，全系统唯一（存储于sys_attach_file_convter表的fdConverterKey字段）
     */
    private String id;

    /**
     * 附件转换器名称(如：jpg转换器等)
     */
    private String messageKey;

    /**
     * 转换源文件扩展名
     */
    private String sourceFileExtName;

    /**
     * 转换目标文件扩展名
     */
    private String targetFileExtName;

    /**
     * 优先级，源和目标都相同时，优先级高的转换器优先被选中
     */
    private int priority;

    /**
     * 附件存储扩展接口的实现
     */
    @BaseOnProperty
    private ISysAttachConverter sysAttachConverter;

}
