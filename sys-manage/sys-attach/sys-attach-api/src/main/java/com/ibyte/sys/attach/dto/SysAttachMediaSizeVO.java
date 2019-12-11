package com.ibyte.sys.attach.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Getter
@Setter
@ToString
public class SysAttachMediaSizeVO {

    /**
     * 图片或视频宽度(像素)
     */
    private Integer width;

    /**
     * 图片或视频高度(像素)
     */
    private Integer height;

    /**
     * 视频帧率
     */
    private Integer frameRate;

    /**
     * 视频时长
     */
    private Long duration;
}