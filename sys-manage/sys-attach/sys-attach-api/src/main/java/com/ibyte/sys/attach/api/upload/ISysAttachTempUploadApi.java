package com.ibyte.sys.attach.api.upload;

import com.ibyte.sys.attach.dto.SysAttachUploadBytesVO;
import com.ibyte.sys.attach.dto.slice.SysAttachUploadSliceBytesVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 临时附件上传API
 * <P/>客户端调用API请使用包装接口
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
public interface ISysAttachTempUploadApi {

    /**
     * 上传临时附件
     * @return 上传后得到的附件ID
     */
    @PostMapping("/addTemp")
    String addTemp(@RequestBody SysAttachUploadBytesVO uploadBytesVO);

    /**
     * 上传临时附件
     * <P/>使用分片上传模式
     * @return 如果全部分片上传完成，将返回生成的附件ID，否则返回null
     */
    @PostMapping("/sliceTemp")
    String sliceTemp(@RequestBody SysAttachUploadSliceBytesVO sliceBytesVO);
}
