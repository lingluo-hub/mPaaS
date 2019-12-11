package com.ibyte.attach.core.entity;

import com.ibyte.common.core.data.field.FdCreateTime;
import com.ibyte.common.core.data.field.FdLastModifiedTime;
import com.ibyte.common.core.entity.AbstractEntity;
import com.ibyte.common.util.IDGenerator;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 文件分片碎片
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Setter
@Getter
@Entity
@Table(name = "sys_attach_file_slice")
public class SysAttachFileSlice extends AbstractEntity implements FdCreateTime, FdLastModifiedTime {

    /**
     * 分片概要ID
     */
    @Column
    @Length(max = IDGenerator.LEN)
    @NotBlank
    private String fdSliceSummaryId;

    /**
     * 当前分片序号
     */
    @Column(nullable=false)
    @NotNull
    private Integer fdSliceIndex;

    /**
     * 当前分片大小（字节）
     */
    @Column
    @NotNull
    private Long fdSliceSize;

    /**
     * 分片相对路径（以/开头，到文件名，一般以id为文件名且不存储扩展名）
     */
    @Column
    @Length(max = 255)
    @NotBlank
    private String fdFilePath;

    /**
     * 加密方式
     */
    @Column
    @Length(max = 10)
    private String fdEncryptMethod;

    /**
     * 一级目录（多对一）
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fd_catalog_id")
    private SysAttachCatalog fdSysAttachCatalog;

    /**
     * 模块目录（多对一）
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fd_module_location_id")
    private SysAttachModuleLocation fdSysAttachModuleLocation;

    /**
     * 获取文件存储的全路径
     */
    @Transient
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