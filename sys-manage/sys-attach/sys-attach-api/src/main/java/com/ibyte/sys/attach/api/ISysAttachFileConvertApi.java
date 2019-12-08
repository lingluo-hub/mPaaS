package com.ibyte.sys.attach.api;

import com.ibyte.sys.attach.dto.SysAttachFileConvertVO;
import com.ibyte.sys.attach.dto.SysAttachFileVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 附件转换API
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
public interface ISysAttachFileConvertApi {

    /**
     * 生成文件转换VO
     */
    @PostMapping("/preCconvert")
    SysAttachFileConvertVO preConvert(@RequestBody SysAttachFileVO attachFileVO);

    /**
     * 保存转换后的fileConvert
     */
    @PostMapping("/save")
    void saveAttachFileConvert(@RequestBody SysAttachFileConvertVO fileConvertVO);
}
