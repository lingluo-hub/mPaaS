package com.ibyte.attach.core.api.store;

import com.ibyte.attach.core.dto.SysAttachUploadResultVO;
import com.ibyte.attach.core.entity.SysAttachCatalog;
import com.ibyte.attach.core.entity.SysAttachModuleLocation;
import com.ibyte.attach.core.service.SysAttachCatalogService;
import com.ibyte.attach.core.service.SysAttachModuleLocationService;
import com.ibyte.common.exception.KmssServiceException;
import com.ibyte.common.util.IDGenerator;
import com.ibyte.sys.attach.support.store.ISysAttachStoreProxy;
import com.ibyte.sys.attach.support.store.SysAttachStoreFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    protected SysAttachCatalogService sysAttachCatalogService;

    @Autowired
    protected SysAttachModuleLocationService sysAttachModuleLocationService;

    @Autowired
    protected SysAttachStoreFactory sysAttachStoreFactory;

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
        SysAttachCatalog sysAttachCatalog = sysAttachCatalogService.findDefaultCatalog();
        String catalog = sysAttachCatalog.getFdServerPath();

        // 获取模块目录
        SysAttachModuleLocation moduleLocation =
                entityName != null ? sysAttachModuleLocationService.findCurrentByEntity(entityName) : null;
        String modelPath = moduleLocation != null ? moduleLocation.getFdModelPath() : "";

        // 生成文件ID
        String fileId = IDGenerator.generateID();

        // 按日期生成相对路径
        String filePath = "/" + formatter.format(LocalDate.now()) + "/" + fileId;

        // 获取默认存储扩展
        ISysAttachStoreProxy storeProxy = sysAttachStoreFactory.getAttachStoreService().getProxyService();
        // 得到存储全路径
        String fullPath = storeProxy.buildFullPath(catalog, modelPath, filePath);

        try {
            writeFunction.accept(storeProxy, fullPath);
        } catch (Exception e) {
            log.warn("附件上传写入文件系统失败", e);
            throw new KmssServiceException("sys-attach:sys.attach.msg.error.SysAttachWriteFailed", e);
        }
        return new SysAttachUploadResultVO(fileId, filePath, fullPath, sysAttachCatalog, moduleLocation);
    }

    /**
     * 获取默认的存储扩展点
     */
    public String getDefaultStoreLocation() {
        return this.sysAttachStoreFactory.getDefaultLocation();
    }

    /**
     * 获取存储实现
     */
    public ISysAttachStoreProxy getStoreProxy() {
        return sysAttachStoreFactory.getAttachStoreService().getProxyService();
    }
}
