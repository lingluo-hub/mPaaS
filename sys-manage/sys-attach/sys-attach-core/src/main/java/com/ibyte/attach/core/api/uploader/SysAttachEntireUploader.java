package com.ibyte.attach.core.api.uploader;

import com.ibyte.attach.core.api.store.SysAttachStoreService;
import com.ibyte.attach.core.dto.SysAttachUploadResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * 完整上传实现
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Service
public class SysAttachEntireUploader {

    @Autowired
    protected SysAttachStoreService sysAttachStoreService;

    public SysAttachUploadResultVO upload(InputStream inputStream, String entityName) {
        return sysAttachStoreService.writeNew(inputStream, entityName);
    }
}
