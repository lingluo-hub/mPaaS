package com.ibyte.sys.attach.constant;

import com.ibyte.common.core.constant.IEnum;
import lombok.AllArgsConstructor;

/**
 * 附件时效类型
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@AllArgsConstructor
public enum AttachEffectTypeEnum implements IEnum<String> {

    /** 已保存的附件 */
    PERSISTENT("PERSISTENT", "sys-attach:enum.AttachEffectType.PERSISTENT"),

    /** 尚未关联主文档的附件 */
    TRANSIENT("TRANSIENT", "sys-attach:enum.AttachEffectType.TRANSIENT"),

    /** 临时存储的文件 */
    TEMPORARY("TEMPORARY", "sys-attach:enum.AttachEffectType.TEMPORARY");

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
            extends IEnum.Converter<String, AttachEffectTypeEnum> {
    }
}
