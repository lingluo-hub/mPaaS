package com.ibyte.sys.attach.support.convert;

import com.ibyte.sys.attach.dto.SysAttachFileConvertVO;

/**
 * 附件转换扩展点必须实现的接口
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
public interface ISysAttachConverter {

    /**
     * 转换附件file
     * @param fileConvert
     */
    void convert(SysAttachFileConvertVO fileConvert);
}