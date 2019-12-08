package com.ibyte.attach.core.api.store;

import com.ibyte.attach.core.dto.SysAttachUploadResultVO;
import com.ibyte.attach.core.entity.SysAttachCatalog;
import com.ibyte.sys.attach.support.store.ISysAttachStoreProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.BiConsumer;

/**
 * 附件存储service
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Slf4j
@Service
public class SysAttachStoreService {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/M/d");

    /**
     * 普通上传
     */
    public SysAttachUploadResultVO writeNew(InputStream inputStream, String entityName) {
        // 定义写入文件系统的操作函数
        BiConsumer<ISysAttachStoreProxy, String> writeOpt = (storeProxy, path) -> storeProxy.writeFile(inputStream,
                path);
        // 写入文件系统
        return this.doUpload(entityName, writeOpt);
    }

    /**
     * 将数据写入到文件系统
     * @param entityName 业务实体名，用于确定文件保存目录
     * @param writeFunction 写入操作逻辑，需要调用方实现
     */
    private SysAttachUploadResultVO doUpload(String entityName, BiConsumer<ISysAttachStoreProxy, String> writeFunction) {
        // 获取一级目录

        // 获取模块目录

        // 生成文件ID

        // 按日期生成相对路径

        // 获取默认存储扩展

        // 得到存储全路径
        return null;
    }
}
