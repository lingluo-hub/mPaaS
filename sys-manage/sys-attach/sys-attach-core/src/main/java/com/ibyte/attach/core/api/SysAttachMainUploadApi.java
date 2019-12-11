package com.ibyte.attach.core.api;

import com.ibyte.sys.attach.api.upload.ISysAttachMainUploadApi;
import com.ibyte.attach.core.api.operation.upload.SysAttachMainUploadOperationService;
import com.ibyte.sys.attach.dto.SysAttachUploadBytesVO;
import com.ibyte.sys.attach.dto.slice.SysAttachUploadSliceBytesVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 上传附件API
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Slf4j
@Service
@RestController
@RequestMapping("/api/sys-attach/main/")
public class SysAttachMainUploadApi implements ISysAttachMainUploadApi {

    @Autowired
    private SysAttachMainUploadOperationService sysAttachMainUploadOperationService;


    @Override
    @Transactional
    public String addByEntity(SysAttachUploadBytesVO uploadBytesVO) {
        return sysAttachMainUploadOperationService.addByEntity(uploadBytesVO);
    }

    @Override
    @Transactional
    public String sliceByEntity(SysAttachUploadSliceBytesVO sliceBytesVO) {
        return sysAttachMainUploadOperationService.sliceByEntity(sliceBytesVO);
    }

    @Override
    public String addUniqueByEntity(SysAttachUploadBytesVO uploadBytesVO) {
        return null;
    }

    @Override
    public String sliceUniqueByEntity(SysAttachUploadSliceBytesVO sliceBytesVO) {
        return null;
    }
}
