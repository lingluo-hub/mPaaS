package com.ibyte.sys.attach.api.upload;

import com.ibyte.sys.attach.dto.SysAttachUploadBytesVO;
import com.ibyte.sys.attach.dto.slice.SysAttachUploadSliceBytesVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 普通附件上传API
 * <P/>客户端调用API请使用包装接口
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
public interface ISysAttachMainUploadApi {

    /**
     * 根据主文档添加附件
     * @return 上传后得到的附件ID
     */
    @PostMapping("/addByEntity")
    String addByEntity(@RequestBody SysAttachUploadBytesVO uploadBytesVO);

    /**
     * 根据主文档添加附件
     * <P/>使用分片上传模式
     * @return 如果全部分片上传完成，将返回生成的附件ID，否则返回null
     */
    @PostMapping("/sliceByEntity")
    String sliceByEntity(@RequestBody SysAttachUploadSliceBytesVO sliceBytesVO);

    /**
     * 根据主文档和key添加唯一的附件
     * <P/>该key下其他附件会被删除，只保留本次添加的附件
     * @return 上传后得到的附件ID
     */
    @PostMapping("/addUniqueByEntity")
    String addUniqueByEntity(@RequestBody SysAttachUploadBytesVO uploadBytesVO);

    /**
     * 根据主文档和key添加唯一的附件
     * <P/>该key下其他附件会被删除，只保留本次添加的附件
     * <P/>使用分片上传模式
     * @return 如果全部分片上传完成，将返回生成的附件ID，否则返回null
     */
    @PostMapping("/sliceUniqueByEntity")
    String sliceUniqueByEntity(@RequestBody SysAttachUploadSliceBytesVO sliceBytesVO);
}
