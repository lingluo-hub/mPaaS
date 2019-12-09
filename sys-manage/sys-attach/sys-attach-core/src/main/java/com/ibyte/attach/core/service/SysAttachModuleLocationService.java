package com.ibyte.attach.core.service;

import com.ibyte.attach.core.entity.SysAttachModuleLocation;
import com.ibyte.attach.core.repository.SysAttachModuleLocationRepository;
import com.ibyte.common.core.dto.QueryRequest;
import com.ibyte.common.core.query.IteratorQueryTemplate;
import com.ibyte.common.core.service.AbstractServiceImpl;
import com.ibyte.sys.attach.dto.SysAttachModuleLocationVO;
import org.springframework.stereotype.Service;

import java.util.Iterator;

/**
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Service
public class SysAttachModuleLocationService extends
        AbstractServiceImpl<SysAttachModuleLocationRepository, SysAttachModuleLocation, SysAttachModuleLocationVO> {

    public SysAttachModuleLocation findCurrentByEntity(String entityName) {
        IteratorQueryTemplate<SysAttachModuleLocation> template = new IteratorQueryTemplate<>(entityManager, SysAttachModuleLocation.class);
        template.setFetchSize(1);
        QueryRequest request = new QueryRequest();
        request.addCondition("fdEntityName", entityName);
        request.addCondition("fdIsCurrent", true);
        Iterator<SysAttachModuleLocation> iterator = template.iterator(request);
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }
}
