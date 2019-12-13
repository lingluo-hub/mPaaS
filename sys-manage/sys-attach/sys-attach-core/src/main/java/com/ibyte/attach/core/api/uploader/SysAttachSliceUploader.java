package com.ibyte.attach.core.api.uploader;

import com.ibyte.attach.core.api.store.SysAttachStoreService;
import com.ibyte.attach.core.api.uploader.slice.SysAttachSliceAppendUploader;
import com.ibyte.attach.core.dto.SysAttachUploadResultVO;
import com.ibyte.attach.core.entity.SysAttachFileSlice;
import com.ibyte.attach.core.entity.SysAttachFileSliceSummary;
import com.ibyte.attach.core.service.SysAttachFileSliceService;
import com.ibyte.attach.core.service.SysAttachFileSliceSummaryService;
import com.ibyte.common.exception.ParamsNotValidException;
import com.ibyte.common.util.IDGenerator;
import com.ibyte.sys.attach.dto.SysAttachFileSliceSummaryVO;
import com.ibyte.sys.attach.dto.slice.SysAttachFileSliceVO;
import com.ibyte.sys.attach.dto.slice.SysAttachUploadSliceBaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/**
 * 分片模式上传
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Service
public class SysAttachSliceUploader {

    @Autowired
    private SysAttachSliceAppendUploader sliceAppendUploader;

    @Autowired
    protected SysAttachStoreService sysAttachStoreService;


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

        // 根据分片概要查询上一个分片碎片
        SysAttachFileSliceVO previousSlice = this.findPreviousSliceBySummaryId(sliceSummary.getFdId());

        // 判断是否可以支持追加写入
        if (this.checkSupportAppend(sliceBaseVO)) {
            // 追加上传的情况下，必须按照顺序上传分片
            if (!this.checkUploadingInOrder(previousSlice, sliceBaseVO)) {
                throw new ParamsNotValidException("顺序上传模式必须按照顺序上传分片");
            }

            // 追加写入实现
            SysAttachFileSlice currentSlice = sliceAppendUploader.uploadSlice(inputStream, sliceBaseVO,
                    sliceSummary.getFdId(), previousSlice);

            // 判断是否最后一个分片
            if (this.isLastSlice(sliceBaseVO)) {
                // 创建完整附件，并返回临时附件文件ID
                return sliceAppendUploader.createFullAttach(sliceBaseVO, Collections.singletonList(currentSlice), sliceSummary.getFdId());
            }


        } else {

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

    /**
     * 根据分片概要查询最近的一个分片
     */
    private SysAttachFileSliceVO findPreviousSliceBySummaryId(String sliceSummaryId) {
        return sysAttachFileSliceService.findLatestOneBySummaryId(sliceSummaryId, null);
    }

    /**
     * 检查是否支持顺序上传
     */
    private boolean checkSupportAppend(SysAttachUploadSliceBaseVO sliceBaseVO) {
        return sliceBaseVO.isFdOrderUpload() && sysAttachStoreService.fileStoreSupportAppend();
    }

    /**
     * 检查当前上传是否遵守顺序上传
     */
    private boolean checkUploadingInOrder(SysAttachFileSliceVO previousSlice, SysAttachUploadSliceBaseVO sliceBaseVO) {
        // 如果不存在上一个分片，则当前分片序号必须为0
        if (previousSlice == null && sliceBaseVO.getFdSliceIndex() == 0) {
            return Boolean.TRUE;
        }
        // 如果存在上一个分片，则当前分片序号必须为上一个分片序号+1
        if (previousSlice != null && sliceBaseVO.getFdSliceIndex() == previousSlice.getFdSliceIndex() + 1) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 判断当前分片是否是最后一个分片
     */
    private boolean isLastSlice(SysAttachUploadSliceBaseVO sliceBaseVO) {
        return sliceBaseVO.getFdSliceIndex() == sliceBaseVO.getFdTotalSlices() - 1;
    }

}
