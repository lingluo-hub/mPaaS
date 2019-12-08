package com.ibyte.sys.attach.constant;

import com.ibyte.common.core.constant.IEnum;
import lombok.AllArgsConstructor;

/**
 * 附件状态
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@AllArgsConstructor
public enum AttachStatusEnum implements IEnum<String> {

    /** 有效 */
    VALID("VALID", "sys-attach:enum.AttachStatus.VALID"),

    /** 已过期删除 */
    EXPIRED("EXPIRED", "sys-attach:enum.AttachStatus.EXPIRED"),

    /** 已人工删除 */
    DELETED("DELETED", "sys-attach:enum.AttachStatus.DELETED");

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
            extends IEnum.Converter<String, AttachStatusEnum> {
    }
}

