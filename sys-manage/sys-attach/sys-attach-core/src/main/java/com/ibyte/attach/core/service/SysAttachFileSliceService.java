package com.ibyte.attach.core.service;

import com.ibyte.attach.core.entity.SysAttachFileSlice;
import com.ibyte.attach.core.repository.SysAttachFileSliceRepository;
import com.ibyte.common.core.constant.QueryConstant;
import com.ibyte.common.core.dto.QueryRequest;
import com.ibyte.common.core.dto.QueryResult;
import com.ibyte.common.core.query.PageQueryTemplate;
import com.ibyte.common.core.service.AbstractServiceImpl;
import com.ibyte.sys.attach.dto.slice.SysAttachFileSliceVO;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Service
public class SysAttachFileSliceService extends AbstractServiceImpl<SysAttachFileSliceRepository, SysAttachFileSlice,
        SysAttachFileSliceVO> {

    /**
     * 根据分片概要查询最新一个分片
     * @param sliceSummaryId 分片概要ID
     */
    public SysAttachFileSliceVO findLatestOneBySummaryId(String sliceSummaryId, Integer fdSliceIndex) {
        PageQueryTemplate<SysAttachFileSlice, SysAttachFileSliceVO> template = new PageQueryTemplate<>(entityManager,
                getEntityClass(), getViewObjectClass());
        QueryRequest request = new QueryRequest();
        request.setPageSize(1);
        // 跟据分片概要ID查询
        request.addCondition("fdSliceSummaryId", sliceSummaryId);
        if (fdSliceIndex != null) {
            request.addCondition("fdSliceIndex", fdSliceIndex);
        }
        // 根据分片序号和上传时间倒序查询
        request.addSort("fdSliceIndex", QueryConstant.Direction.DESC);
        request.addSort("fdCreateTime", QueryConstant.Direction.DESC);

        QueryResult<SysAttachFileSliceVO> result = template.findAll(request);
        if (result.getContent().size() > 0) {
            return result.getContent().get(0);
        }
        return null;
    }
}
