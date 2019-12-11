package com.ibyte.attach.core.repository;

import com.ibyte.attach.core.entity.SysAttachMain;
import com.ibyte.common.core.repository.IRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * 附件
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Repository
public interface SysAttachRepository extends IRepository<SysAttachMain> {

    @Query("select count(fdId) from SysAttachMain where fdSysAttachFile.fdId = ?1 and fdTenantId = ?2")
    long countByFileId(String attachFileId, Integer fdTenantId);
}
