package com.ibyte.sys.attach.dto;

import com.ibyte.sys.attach.constant.ImageRotationEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 图片处理（裁剪/旋转）VO
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Getter
@Setter
@ToString
public class SysAttachImageProcessVO extends SysAttachFileConvertVO {

    /**
     * 裁剪左上角x坐标
     */
    private Integer fdX;

    /**
     * 裁剪左上角y坐标
     */
    private Integer fdY;

    /**
     * 裁剪宽度（像素）
     */
    private Integer fdWidth;

    /**
     * 裁剪高度（像素）
     */
    private Integer fdHeight;

    /**
     * 旋转角度（顺时针，必须为90的倍数，如：90度）
     */
    private ImageRotationEnum fdRotation;
}
