package com.ibyte.attach.core.repository;

import com.ibyte.attach.core.entity.SysAttachModuleLocation;
import com.ibyte.common.core.repository.IRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Repository
public interface SysAttachModuleLocationRepository extends IRepository<SysAttachModuleLocation> {

    @Query("from SysAttachModuleLocation where fdEntityName = :fdEntityName and fdModelPath = :fdModelPath")
    SysAttachModuleLocation findByPath(@Param("fdEntityName") String fdEntityName,
                                       @Param("fdModelPath") String fdModelPath);
}
