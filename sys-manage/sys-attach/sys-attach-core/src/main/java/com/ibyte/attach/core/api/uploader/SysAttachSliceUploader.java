package com.ibyte.attach.core.api.uploader;

import com.ibyte.attach.core.dto.SysAttachUploadResultVO;
import com.ibyte.attach.core.entity.SysAttachFileSliceSummary;
import com.ibyte.attach.core.service.SysAttachFileSliceService;
import com.ibyte.attach.core.service.SysAttachFileSliceSummaryService;
import com.ibyte.common.util.IDGenerator;
import com.ibyte.sys.attach.dto.SysAttachFileSliceSummaryVO;
import com.ibyte.sys.attach.dto.slice.SysAttachFileSliceVO;
import com.ibyte.sys.attach.dto.slice.SysAttachUploadSliceBaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * 分片模式上传
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Service
public class SysAttachSliceUploader {

    @Autowired
    private SysAttachFileSliceService sysAttachFileSliceService;


    @Autowired
    private SysAttachFileSliceSummaryService sysAttachFileSliceSummaryService;

    public SysAttachUploadResultVO upload(InputStream inputStream, SysAttachUploadSliceBaseVO sliceBaseVO) {
        // 根据文件信息查询是否存在分片概要
        SysAttachFileSliceSummary sliceSummary = this.checkExistedSummary(sliceBaseVO);

        // 如果不存在分片概要，则保存分片概要
        if (sliceSummary == null) {
            sliceSummary = this.saveSliceSummary(sliceBaseVO);
        }
        // 查询当前分片是否存在，若存在则不需要上传当前分片
        SysAttachFileSliceVO currentSliceExisted = this.checkSliceExited(sliceSummary.getFdId(),
                sliceBaseVO.getFdSliceIndex());
        if (currentSliceExisted != null) {
            return null;
        }



        return null;
    }

    /**
     * 查询是否存在分片概要信息
     */
    private SysAttachFileSliceSummary checkExistedSummary(SysAttachUploadSliceBaseVO sliceBaseVO) {
        SysAttachFileSliceSummaryVO summaryVO = new SysAttachFileSliceSummaryVO();
        summaryVO.setFdTotalSlices(sliceBaseVO.getFdTotalSlices());
        summaryVO.setFdEachSliceSize(sliceBaseVO.getFdEachSliceSize());
        summaryVO.setFdFileSize(sliceBaseVO.getFdFileSize());
        summaryVO.setFdOrderUpload(sliceBaseVO.isFdOrderUpload());
        summaryVO.setFdMd5(sliceBaseVO.getFdMd5());
        summaryVO.setFdFileName(sliceBaseVO.getFdFileName());
        summaryVO.setFdFileExtName(sliceBaseVO.getFdFileExtName());
        summaryVO.setFdCreatorId(sliceBaseVO.getFdCreatorId());
        return sysAttachFileSliceSummaryService.findExistedSummary(summaryVO);
    }

    /**
     * 保存分片概要
     */
    private SysAttachFileSliceSummary saveSliceSummary(SysAttachUploadSliceBaseVO sliceBaseVO) {
        SysAttachFileSliceSummary sliceSummary = new SysAttachFileSliceSummary();
        sliceSummary.setFdId(IDGenerator.generateID());
        sliceSummary.setFdTotalSlices(sliceBaseVO.getFdTotalSlices());
        sliceSummary.setFdEachSliceSize(sliceBaseVO.getFdEachSliceSize());
        sliceSummary.setFdOrderUpload(sliceBaseVO.isFdOrderUpload());
        sliceSummary.setFdFileSize(sliceBaseVO.getFdFileSize());
        sliceSummary.setFdMd5(sliceBaseVO.getFdMd5());
        sliceSummary.setFdFileName(sliceBaseVO.getFdFileName());
        sliceSummary.setFdFileExtName(sliceBaseVO.getFdFileExtName());
        sliceSummary.setFdCreatorId(sliceBaseVO.getFdCreatorId());
        sysAttachFileSliceSummaryService.add(sliceSummary);
        return sliceSummary;
    }

    /**
     * 检查当前分片是否已经上传过
     */
    private SysAttachFileSliceVO checkSliceExited(String sliceSummaryId, Integer fdSliceIndex) {
        return sysAttachFileSliceService.findLatestOneBySummaryId(sliceSummaryId, fdSliceIndex);
    }

}
