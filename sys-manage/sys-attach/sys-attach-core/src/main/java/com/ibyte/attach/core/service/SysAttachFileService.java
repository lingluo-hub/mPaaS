package com.ibyte.attach.core.service;

import com.ibyte.attach.core.entity.SysAttachFile;
import com.ibyte.attach.core.repository.SysAttachFileRepository;
import com.ibyte.common.core.constant.QueryConstant;
import com.ibyte.common.core.dto.QueryRequest;
import com.ibyte.common.core.query.IteratorQueryTemplate;
import com.ibyte.common.core.service.AbstractServiceImpl;
import com.ibyte.sys.attach.dto.SysAttachFileVO;
import org.springframework.stereotype.Service;

import java.util.Iterator;

/**
 * 附件文件service
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Service
public class SysAttachFileService extends AbstractServiceImpl<SysAttachFileRepository, SysAttachFile, SysAttachFileVO> {

    /**
     * 查询是否已经存在相同的文件
     * <P/>MD5值 文件大小 文件全名完全一致才认为是相同文件
     * @param fdMd5 md5值
     * @param fdFileSize 文件大小
     */
    public SysAttachFile findExistedSameFile(String fdMd5, Long fdFileSize, String fdFileName, String fdFileExtName) {
        IteratorQueryTemplate<SysAttachFile> template = new IteratorQueryTemplate<>(entityManager, SysAttachFile.class);
        template.setFetchSize(1);
        QueryRequest request = new QueryRequest();
        request.addCondition("fdMd5", fdMd5);
        request.addCondition("fdFileSize", fdFileSize);
        request.addCondition("fdFileName", fdFileName);
        request.addCondition("fdFileExtName", fdFileExtName);
        request.addSort("fdCreateTime", QueryConstant.Direction.DESC);
        Iterator<SysAttachFile> iterator = template.iterator(request);
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }
}