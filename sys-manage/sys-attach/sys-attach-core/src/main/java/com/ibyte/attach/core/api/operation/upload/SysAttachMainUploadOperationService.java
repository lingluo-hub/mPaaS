package com.ibyte.attach.core.api.operation.upload;

import com.ibyte.attach.core.api.uploader.SysAttachEntireUploader;
import com.ibyte.attach.core.api.uploader.SysAttachSliceUploader;
import com.ibyte.attach.core.dto.SysAttachUploadResultVO;
import com.ibyte.attach.core.entity.SysAttachFile;
import com.ibyte.attach.core.entity.SysAttachMain;
import com.ibyte.attach.core.service.SysAttachFileService;
import com.ibyte.attach.core.service.SysAttachService;
import com.ibyte.common.constant.NamingConstant;
import com.ibyte.common.core.util.MimeTypeUtil;
import com.ibyte.common.exception.KmssServiceException;
import com.ibyte.common.util.IDGenerator;
import com.ibyte.common.util.TenantUtil;
import com.ibyte.sys.attach.constant.AttachEffectTypeEnum;
import com.ibyte.sys.attach.constant.AttachStatusEnum;
import com.ibyte.sys.attach.dto.SysAttachMediaSizeVO;
import com.ibyte.sys.attach.dto.SysAttachUploadBytesVO;
import com.ibyte.sys.attach.dto.base.SysAttachUploadBaseVO;
import com.ibyte.sys.attach.dto.slice.SysAttachUploadSliceBytesVO;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Optional;
import java.util.function.Function;

/**
 * 普通附件上传操作service
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Service
public class SysAttachMainUploadOperationService extends AbstractSysAttachUploadOperationService {

    @Autowired
    private SysAttachFileService sysAttachFileService;

    @Autowired
    private SysAttachEntireUploader sysAttachEntireUploader;

    @Autowired
    private SysAttachService sysAttachService;

    @Autowired
    private SysAttachSliceUploader sysAttachSliceUploader;

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
     * API上传附件（分片上传）
     * @return 生成的附件ID，若未上传完成则返回null
     */
    @Transactional
    public String sliceByEntity(SysAttachUploadSliceBytesVO sliceBytesVO) {
        // 定义使用分片上传模式，上传API附件的函数
        Function<InputStream, SysAttachUploadResultVO> uploadFunction = inputStream -> sysAttachSliceUploader.upload(inputStream, sliceBytesVO);
        // 执行上传操作逻辑
        return this.recordBytesUpload(sliceBytesVO, new ByteArrayInputStream(sliceBytesVO.getFdFileData()),
                sliceBytesVO.getFdEntityId(), sliceBytesVO.getFdEntityKey(), uploadFunction);
    }

    /**
     * API上传附件，并保存附件记录
     * @param uploadFunction 需要具体实现的文件上传逻辑
     * @return 生成的附件ID
     */
    private String recordBytesUpload(SysAttachUploadBaseVO uploadBaseVO, InputStream inputStream, String entityId, String entityKey,
                                     Function<InputStream, SysAttachUploadResultVO> uploadFunction) {
        try {
            // 从已有文件中查找相同文件
            SysAttachFile sameFile = sysAttachFileService.findExistedSameFile(uploadBaseVO.getFdMd5(),
                    uploadBaseVO.getFdFileSize(), uploadBaseVO.getFdFileName(), uploadBaseVO.getFdFileExtName());
            SysAttachFile attachFile;
            if (sameFile != null) {
                // 若有相同文件则使用相同文件
                attachFile = sameFile;
            } else {
                // 没有则上传新文件
                SysAttachUploadResultVO uploadResultVO = uploadFunction.apply(inputStream);
                if (uploadResultVO == null) {
                    return null;
                }

                // TODO 如果是媒体，获取媒体宽高帧率

                // 保存attachFile
                attachFile = new SysAttachFile();
                attachFile.setFdId(uploadResultVO.getFileId());
                attachFile.setFdFileName(uploadBaseVO.getFdFileName());
                attachFile.setFdFileExtName(uploadBaseVO.getFdFileExtName());
                attachFile.setFdFileSize(uploadBaseVO.getFdFileSize());
                attachFile.setFdMd5(uploadBaseVO.getFdMd5());
                attachFile.setFdFilePath(uploadResultVO.getFilePath());
                attachFile.setFdTenantId(TenantUtil.getTenantId());
                attachFile.setFdCreatorId(uploadBaseVO.getFdCreatorId());
                attachFile.setFdStatus(AttachStatusEnum.VALID);
                attachFile.setFdEncryptMethod(sysAttachConfig.getCurrentEncryMethod());
                attachFile.setFdLocation(sysAttachStoreService.getDefaultStoreLocation());
                attachFile.setFdSysAttachCatalog(uploadResultVO.getSysAttachCatalog());
                attachFile.setFdSysAttachModuleLocation(uploadResultVO.getSysAttachModuleLocation());
                SysAttachMediaSizeVO mediaSizeVO = this.getMediaSize(uploadBaseVO.getFdFileExtName(), uploadResultVO.getFullPath());
                if (mediaSizeVO != null) {
                    attachFile.setFdWidth(mediaSizeVO.getWidth());
                    attachFile.setFdHeight(mediaSizeVO.getHeight());
                }
                sysAttachFileService.add(attachFile);
            }

            // 判断附件ID是否有传入
            String attachId = uploadBaseVO.getOriginAttachId();
            if (attachId == null) {
                // 没有传入附件ID，则新生成附件ID
                attachId = IDGenerator.generateID();
            } else {
                Optional<SysAttachMain> exist = sysAttachService.findById(attachId);
                if (exist.isPresent()) {
                    // 如果有传入附件ID，并且附件ID在系统中已存在，
                    attachId = IDGenerator.generateID();
                }
            }

            // 生成attachMain
            SysAttachMain attachMain = new SysAttachMain();
            // 关联已有文件或新创建的文件
            attachMain.setFdId(attachId);
            attachMain.setFdSysAttachFile(attachFile);
            attachMain.setFdFileName(attachFile.getFdFileName());
            attachMain.setFdFileExtName(attachFile.getFdFileExtName());
            attachMain.setFdFileSize(attachFile.getFdFileSize());
            attachMain.setFdCreatorId(attachFile.getFdCreatorId());
            attachMain.setFdLastModifier(attachFile.getFdLastModifier());
            attachMain.setFdEntityKey(entityKey);
            attachMain.setFdEntityName(uploadBaseVO.getFdEntityName());
            attachMain.setFdEntityId(entityId);
            attachMain.setFdTenantId(TenantUtil.getTenantId());
            attachMain.setFdMimeType(MimeTypeUtil.getMimeType(attachFile.getFdFileName() + NamingConstant.DOT + attachFile.getFdFileExtName()));
            attachMain.setFdExtendInfo(uploadBaseVO.getFdExtendInfo());
            attachMain.setFdStatus(AttachStatusEnum.VALID);
            attachMain.setFdEffectType(AttachEffectTypeEnum.PERSISTENT);
            attachMain.setFdWidth(attachFile.getFdWidth());
            attachMain.setFdHeight(attachFile.getFdHeight());
            attachMain.setFdAnonymous(uploadBaseVO.getFdAnonymous());
            sysAttachService.add(attachMain);
            return attachId;
        } catch (Exception e) {
            throw new KmssServiceException("sys-attach:sys.attach.msg.error.SysAttachWriteFailed", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }


}
