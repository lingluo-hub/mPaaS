package com.ibyte.sys.attach.constant;

import com.ibyte.common.core.constant.IEnum;
import lombok.AllArgsConstructor;

/**
 * 存储目录的状态
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@AllArgsConstructor
public enum AttachCatalogStatusEnum implements IEnum<String> {

    /** 目录可用 */
    VALID("VALID", "sys-attach:enum.AttachCatalogStatus.VALID"),

    /** 目录已满 */
    EXCESSIVE("EXCESSIVE", "sys-attach:enum.AttachCatalogStatus.EXCESSIVE");


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
            extends IEnum.Converter<String, AttachCatalogStatusEnum> {
    }
}