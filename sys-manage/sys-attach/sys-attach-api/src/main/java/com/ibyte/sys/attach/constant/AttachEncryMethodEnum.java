package com.ibyte.sys.attach.constant;

import com.ibyte.common.core.constant.IEnum;
import lombok.AllArgsConstructor;

/**
 * 加密方式（仅对本地文件存储有效）
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@AllArgsConstructor
public enum AttachEncryMethodEnum implements IEnum<String> {

    /** 不加密 */
    NONE("NONE", "sys-attach:enum.AttachEncry.NONE"),

    /** AES加密 */
    AES("AES", "sys-attach:enum.AttachEncry.AES");

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
            extends IEnum.Converter<String, AttachEncryMethodEnum> {
    }
}
