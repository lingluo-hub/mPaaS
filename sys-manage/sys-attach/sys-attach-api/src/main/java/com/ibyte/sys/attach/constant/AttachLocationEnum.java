package com.ibyte.sys.attach.constant;

import com.ibyte.common.core.constant.IEnum;
import lombok.AllArgsConstructor;

/**
 * 附件存储位置
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@AllArgsConstructor
public enum AttachLocationEnum implements IEnum<String> {

    /** 本地文件存储 */
    SERVER("server", "sys-attach:enum.AttachLocation.SERVER"),

    /** 阿里云对象存储 */
    ALIOSS("alioss", "sys-attach:enum.AttachLocation.ALIOSS"),

    /** 腾讯云对象存储 */
    TENCENTCOS("tencentcos", "sys-attach:enum.AttachLocation.TENCENTCOS"),

    /** 百度云对象存储 */
    BAIDUBOS("baidubos", "sys-attach:enum.AttachLocation.BAIDUBOS"),

    /** 华为云对象存储 */
    HHAIWEIOBS("huaiweiobs", "sys-attach:enum.AttachLocation.HHAIWEIOBS"),

    /** 七牛云对象存储 */
    QINIUKODO("qiniukodo", "sys-attach:enum.AttachLocation.QINIUKODO"),

    /** 分布式FastDFS存储 */
    FASTDFS("fastdfs", "sys-attach:enum.AttachLocation.FASTDFS");

    private String value;

    private String messageKey;

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getMessageKey() {
        return messageKey;
    }

    public static class Converter
            extends IEnum.Converter<String, AttachLocationEnum> {
    }
}
