package com.ibyte.sys.attach.dto.slice;

import com.ibyte.common.core.dto.AbstractVO;
import com.ibyte.sys.attach.dto.SysAttachCatalogVO;
import com.ibyte.sys.attach.dto.SysAttachModuleLocationVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 文件分片碎片
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Getter
@Setter
@ToString
public class SysAttachFileSliceVO extends AbstractVO {

    /**
     * 分片概要ID
     */
    private String fdSliceSummaryId;

    /**
     * 当前分片序号
     */
    private Integer fdSliceIndex;

    /**
     * 当前分片大小（字节）
     */
    private Long fdSliceSize;

    /**
     * 分片相对路径（以/开头，到文件名，一般以id为文件名且不存储扩展名）
     */
    private String fdFilePath;

    /**
     * 加密方式
     */
    private String fdEncryptMethod;

    /**
     * 一级目录（多对一）
     */
    private SysAttachCatalogVO fdSysAttachCatalog;

    /**
     * 模块目录（多对一）
     */
    private SysAttachModuleLocationVO fdSysAttachModuleLocation;

    /**
     * 获取文件存储的全路径
     */
    public String getFullPath() {
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append(this.fdSysAttachCatalog.getFdServerPath());
        if (this.fdSysAttachModuleLocation != null) {
            pathBuilder.append(this.fdSysAttachModuleLocation.getFdModelPath());
        }
        pathBuilder.append(this.getFdFilePath());
        return pathBuilder.toString();
    }
}
