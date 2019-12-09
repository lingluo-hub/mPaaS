package com.ibyte.attach.core.service;

import com.ibyte.attach.core.entity.SysAttachCatalog;
import com.ibyte.attach.core.repository.SysAttachCatalogRepository;
import com.ibyte.common.core.dto.QueryRequest;
import com.ibyte.common.core.query.IteratorQueryTemplate;
import com.ibyte.common.core.service.AbstractServiceImpl;
import com.ibyte.sys.attach.dto.SysAttachCatalogVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 附件存储目录service
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Service
public class SysAttachCatalogService extends
        AbstractServiceImpl<SysAttachCatalogRepository, SysAttachCatalog, SysAttachCatalogVO> {


    /**
     * 目录只能增加，增加即修改
     *
     * @param vo
     */
    @Transactional(rollbackFor = {})
    public synchronized void addCatalog(SysAttachCatalogVO vo) {
        SysAttachCatalog entity;
        if ((entity = repository.getDefaultCatalog()) != null) {
            if (entity.getFdServerPath().equals(vo.getFdServerPath())) {
                // 目录存在且是当前目录，则返回
                return;
            }
            // 取消当前目录
            entity.setFdIsDefault(false);
            update(entity);
        }
        if ((entity = repository.findByFdServerPath(vo.getFdServerPath())) != null) {
            // 目录存在且不是当前目录，则更改为当前目录
            entity.setFdIsDefault(true);
            update(entity);
        } else {
            // 新增一条记录，并设为默认目录
            vo.setFdIsDefault(true);
            add(vo);
        }
    }

    /**
     * 获取目录列表
     */
    public List<SysAttachCatalog> findCatalogList(QueryRequest queryRequest) {
        IteratorQueryTemplate<SysAttachCatalog> template = new IteratorQueryTemplate<>(entityManager, SysAttachCatalog.class);
        IteratorQueryTemplate.QueryIterator iterator = template.iterator(queryRequest);
        List<SysAttachCatalog> list = new ArrayList<>();
        while (iterator.hasNext()) {
            list.add((SysAttachCatalog) iterator.next());
        }
        return list;
    }

    /**
     * 查询当前目录
     */
    public SysAttachCatalog findDefaultCatalog() {
        return repository.getDefaultCatalog();
    }
}
