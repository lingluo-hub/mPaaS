package com.ibyte.attach.core.api.operation.upload;

import com.ibyte.attach.core.api.uploader.SysAttachEntireUploader;
import com.ibyte.attach.core.dto.SysAttachUploadResultVO;
import com.ibyte.sys.attach.dto.SysAttachUploadBytesVO;
import com.ibyte.sys.attach.dto.base.SysAttachUploadBaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.function.Function;

/**
 * 普通附件上传操作service
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Service
public class SysAttachMainUploadOperationService extends AbstractSysAttachUploadOperationService {

    @Autowired
    private SysAttachEntireUploader sysAttachEntireUploader;

    /**
     * API上传附件
     * @return 生成的附件ID
     */
    @Transactional
    public String addByEntity(SysAttachUploadBytesVO uploadBytesVO) {
        // 定义使用完整上传模式，上传API附件的函数
        Function<InputStream, SysAttachUploadResultVO> uploadFunction = inputStream -> sysAttachEntireUploader.upload(inputStream, uploadBytesVO.getFdEntityName());
        // 执行上传操作逻辑
        return this.recordBytesUpload(uploadBytesVO, new ByteArrayInputStream(uploadBytesVO.getFdFileData()),
                uploadBytesVO.getFdEntityId(), uploadBytesVO.getFdEntityKey(), uploadFunction);
    }

    /**
     * API上传附件，并保存附件记录
     * @param uploadFunction 需要具体实现的文件上传逻辑
     * @return 生成的附件ID
     */
    private String recordBytesUpload(SysAttachUploadBaseVO uploadBaseVO, InputStream inputStream, String entityId, String entityKey,
                                     Function<InputStream, SysAttachUploadResultVO> uploadFunction) {
        // 从已有文件中查找相同文件

        // 没有则上传新文件

        // 判断附件ID是否有传入

        // 关联已有文件或新创建的文件
        return null;
    }
}
