package com.ibyte.attach.core.repository;

import com.ibyte.attach.core.entity.SysAttachCatalog;
import com.ibyte.common.core.repository.IRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Repository
public interface SysAttachCatalogRepository extends IRepository<SysAttachCatalog> {

    @Query("from SysAttachCatalog where fdStatus = 'VALID' and fdIsDefault = true")
    SysAttachCatalog getDefaultCatalog();

    @Query("from SysAttachCatalog where fdStatus = 'VALID' and fdServerPath = :fdServerPath")
    SysAttachCatalog findByFdServerPath(@Param("fdServerPath") String fdServerPath);
}
