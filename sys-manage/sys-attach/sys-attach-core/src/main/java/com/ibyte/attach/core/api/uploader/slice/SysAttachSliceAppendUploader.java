package com.ibyte.attach.core.api.uploader.slice;

import com.ibyte.attach.core.api.store.SysAttachStoreService;
import com.ibyte.attach.core.dto.SysAttachUploadResultVO;
import com.ibyte.attach.core.entity.SysAttachFileSlice;
import com.ibyte.attach.core.service.SysAttachFileSliceService;
import com.ibyte.common.core.mapper.VoToEntityMapper;
import com.ibyte.common.exception.KmssServiceException;
import com.ibyte.sys.attach.dto.slice.SysAttachFileSliceVO;
import com.ibyte.sys.attach.dto.slice.SysAttachUploadSliceBaseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 分片上传逻辑实现：追加写入
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Slf4j
@Service
public class SysAttachSliceAppendUploader extends AbstractSysAttachSliceUploader {

    @Autowired
    protected SysAttachStoreService sysAttachStoreService;

    @Autowired
    private SysAttachFileSliceService fileSliceService;

    @Override
    public SysAttachFileSlice uploadSlice(InputStream inputStream, SysAttachUploadSliceBaseVO sliceBaseVO, String sliceSummaryId, SysAttachFileSliceVO previousSliceVO) {
        try {
            int length = inputStream.available();
            if (previousSliceVO != null) {
                // 如果存在上一个分片碎片，则追加写入
                Long lastSliceSize = previousSliceVO.getFdSliceSize();
                // 原有的分片大小增加
                previousSliceVO.setFdSliceSize(lastSliceSize + length);
                // 原有的分片序号加1
                previousSliceVO.setFdSliceIndex(sliceBaseVO.getFdSliceIndex());
                // 更新原有的分片
                fileSliceService.update(previousSliceVO);

                String appendFullPath = previousSliceVO.getFullPath();
                // 往appendFullPath追加写入文件
                sysAttachStoreService.append(inputStream, appendFullPath, lastSliceSize);

                SysAttachFileSlice previousSlice = new SysAttachFileSlice();
                VoToEntityMapper.getInstance().voToEntity(previousSliceVO, previousSlice);
                return previousSlice;
            } else {
                // 如果不存在，则写入新文件
                SysAttachUploadResultVO resultVO = sysAttachStoreService.appendFirst(inputStream, sliceBaseVO.getFdEntityName());
                // 保存文件碎片记录
                return super.saveAttachFileSlice(resultVO, sliceBaseVO.getFdSliceIndex(), (long) length,
                        sliceSummaryId);
            }
        } catch (IOException e) {
            log.warn("追加写入文件系统失败，获取流长度异常", e);
            throw new KmssServiceException("sys-attach:sys.attach.msg.error.SysAttachWriteFailed", e);
        }
    }

    /**
     * 生成完整附件（WEB端上传）
     * @return 返回生成的临时附件文件的ID
     */
    @Override
    public SysAttachUploadResultVO createFullAttach(SysAttachUploadSliceBaseVO sliceBaseVO,
                                                    List<SysAttachFileSlice> sliceList, String summaryId) {
        SysAttachFileSlice currentSlice = sliceList.get(0);

        SysAttachUploadResultVO uploadResultVO = new SysAttachUploadResultVO();
        uploadResultVO.setFileId(currentSlice.getFdId());
        uploadResultVO.setFilePath(currentSlice.getFdFilePath());
        uploadResultVO.setFullPath(currentSlice.getFullPath());
        uploadResultVO.setSysAttachCatalog(currentSlice.getFdSysAttachCatalog());
        uploadResultVO.setSysAttachModuleLocation(currentSlice.getFdSysAttachModuleLocation());

        // 异步删除分片记录和分片概要
        CompletableFuture.runAsync(() -> {
            // 删除分片记录
            deleteSliceRecord(currentSlice.getFdId());
            // 删除分片概要
            deleteSliceSummary(summaryId);
        });

        return uploadResultVO;
    }
}
