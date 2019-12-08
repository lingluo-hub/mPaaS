package com.ibyte.sys.attach.constant;

import com.ibyte.common.core.constant.IEnum;
import lombok.AllArgsConstructor;

/**
 * 图片旋转角度
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@AllArgsConstructor
public enum ImageRotationEnum implements IEnum<Integer> {

    /** 90度 */
    DEGREES_90(90, "sys-attach:enum.ImageRotationEnum.DEGREES_90"),

    /** 180度 */
    DEGREES_180(180, "sys-attach:enum.ImageRotationEnum.DEGREES_180"),

    /** 270度 */
    DEGREES_270(270, "sys-attach:enum.ImageRotationEnum.DEGREES_270");

    private Integer value;

    private String messageKey;

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getMessageKey() {
        return messageKey;
    }

    public static class Converter extends IEnum.Converter<Integer, ImageRotationEnum> {
    }
}