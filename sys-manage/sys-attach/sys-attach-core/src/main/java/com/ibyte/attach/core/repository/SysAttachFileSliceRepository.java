package com.ibyte.attach.core.repository;

import com.ibyte.attach.core.entity.SysAttachFileSlice;
import com.ibyte.common.core.repository.IRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 分片碎片
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Repository
public interface SysAttachFileSliceRepository extends IRepository<SysAttachFileSlice> {

    @Query("from SysAttachFileSlice where fdSliceSummaryId = ?1 order by fdSliceIndex asc")
    List<SysAttachFileSlice> findBySummaryId(String summaryId);

    @Modifying
    @Query("delete from SysAttachFileSlice where fdSliceSummaryId = ?1")
    void deleteBySummaryId(String summaryId);
}