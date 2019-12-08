package com.ibyte.sys.attach.constant;

import com.ibyte.common.core.constant.IEnum;
import lombok.AllArgsConstructor;

/**
 * 附件类型
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@AllArgsConstructor
public enum AttachTypeEnum implements IEnum<String> {

    /** 图片文件 */
    PIC("PIC", "sys-attach:enum.AttachType.PIC"),

    /** Office文件 */
    OFFICE("OFFICE", "sys-attach:enum.AttachType.OFFICE"),

    /** 通用文件 */
    BYTE("BYTE", "sys-attach:enum.AttachType.BYTE");

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
            extends IEnum.Converter<String, AttachTypeEnum> {
    }
}
