package com.ibyte.attach.core.service;

import com.ibyte.attach.core.entity.SysAttachFileSliceSummary;
import com.ibyte.attach.core.repository.SysAttachFileSliceSummaryRepository;
import com.ibyte.common.core.constant.QueryConstant;
import com.ibyte.common.core.dto.QueryRequest;
import com.ibyte.common.core.query.IteratorQueryTemplate;
import com.ibyte.common.core.service.AbstractServiceImpl;
import com.ibyte.sys.attach.dto.SysAttachFileSliceSummaryVO;
import org.springframework.stereotype.Service;

import java.util.Iterator;

/**
 * 分片概要service
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Service
public class SysAttachFileSliceSummaryService extends AbstractServiceImpl<SysAttachFileSliceSummaryRepository,
        SysAttachFileSliceSummary, SysAttachFileSliceSummaryVO> {

    /**
     * 查询分片概要是否已存在
     */
    public SysAttachFileSliceSummary findExistedSummary(SysAttachFileSliceSummaryVO summaryVO) {
        IteratorQueryTemplate<SysAttachFileSliceSummary> template = new IteratorQueryTemplate<>(entityManager, SysAttachFileSliceSummary.class);
        template.setFetchSize(1);
        QueryRequest request = new QueryRequest();
        request.addCondition("fdMd5", summaryVO.getFdMd5());
        request.addCondition("fdFileSize", summaryVO.getFdFileSize());
        request.addCondition("fdFileName", summaryVO.getFdFileName());
        request.addCondition("fdFileExtName", summaryVO.getFdFileExtName());
        request.addCondition("fdTotalSlices", summaryVO.getFdTotalSlices());
        request.addCondition("fdEachSliceSize", summaryVO.getFdEachSliceSize());
        request.addCondition("fdOrderUpload", summaryVO.getFdOrderUpload());
        request.addCondition("fdCreatorId", summaryVO.getFdCreatorId());
        request.addSort("fdCreateTime", QueryConstant.Direction.DESC);
        Iterator<SysAttachFileSliceSummary> iterator = template.iterator(request);
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }
}
