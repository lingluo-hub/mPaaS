package com.ibyte.attach.core.api.uploader.slice;

import com.ibyte.attach.core.dto.SysAttachUploadResultVO;
import com.ibyte.attach.core.entity.SysAttachFileSlice;
import com.ibyte.attach.core.service.SysAttachFileSliceService;
import com.ibyte.attach.core.service.SysAttachFileSliceSummaryService;
import com.ibyte.common.core.dto.IdVO;
import com.ibyte.sys.attach.config.SysAttachConfig;
import com.ibyte.sys.attach.dto.slice.SysAttachFileSliceVO;
import com.ibyte.sys.attach.dto.slice.SysAttachUploadSliceBaseVO;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.util.List;

/**
 * 分片上传逻辑实现基类
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
public abstract class  AbstractSysAttachSliceUploader {

    @Autowired
    private SysAttachFileSliceSummaryService sysAttachFileSliceSummaryService;

    @Autowired
    protected SysAttachFileSliceService sysAttachFileSliceService;

    @Autowired
    private SysAttachConfig sysAttachConfig;

    /**
     * 分片上传操作
     */
    public abstract SysAttachFileSlice uploadSlice(InputStream inputStream, SysAttachUploadSliceBaseVO sliceBaseVO,
                                                   String sliceSummaryId, SysAttachFileSliceVO previousSliceVO);

    /**
     * 保存分片记录
     */
    SysAttachFileSlice saveAttachFileSlice(SysAttachUploadResultVO uploadResultVO, Integer sliceIndex, Long fileSize,
                                           String sliceSummaryId) {
        SysAttachFileSlice slice = new SysAttachFileSlice();
        slice.setFdId(uploadResultVO.getFileId());
        slice.setFdSliceIndex(sliceIndex);
        slice.setFdSliceSize(fileSize);
        slice.setFdSliceSummaryId(sliceSummaryId);
        slice.setFdFilePath(uploadResultVO.getFilePath());
        slice.setFdEncryptMethod(sysAttachConfig.getCurrentEncryMethod());
        slice.setFdSysAttachCatalog(uploadResultVO.getSysAttachCatalog());
        slice.setFdSysAttachModuleLocation(uploadResultVO.getSysAttachModuleLocation());
        sysAttachFileSliceService.add(slice);
        return slice;
    }
    /**
     * 生成完整附件（WEB端上传）
     * @return 返回生成的临时附件文件的ID
     */
    public abstract SysAttachUploadResultVO createFullAttach(SysAttachUploadSliceBaseVO sliceBaseVO,
                                                             List<SysAttachFileSlice> sliceList, String summaryId);

    /**
     * 删除分片记录
     */
    void deleteSliceRecord(String sliceId) {
        sysAttachFileSliceService.delete(IdVO.of(sliceId));
    }

    /**
     * 删除分片概要
     */
    void deleteSliceSummary(String sliceSummaryId) {
        sysAttachFileSliceSummaryService.delete(IdVO.of(sliceSummaryId));
    }
}
