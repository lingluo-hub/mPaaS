package com.ibyte.sys.attach.dto.config;

import lombok.Getter;
import lombok.Setter;

/**
 * 附件功能租户配置对象
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Getter
@Setter
public class SysAttachConfigVO {

    /**
     * 临时附件超时时间（秒）
     */
    private Integer tempOverdueSeconds;

    /**
     * 图片转换像素
     * [512,1024]
     */
    private String imageConvertPix;
}
