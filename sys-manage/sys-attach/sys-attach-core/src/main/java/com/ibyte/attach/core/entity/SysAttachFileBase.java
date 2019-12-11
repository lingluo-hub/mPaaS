package com.ibyte.attach.core.entity;

import com.ibyte.common.core.data.field.FdCreateTime;
import com.ibyte.common.core.data.field.FdLastModifiedTime;
import com.ibyte.common.core.entity.AbstractEntity;
import com.ibyte.common.util.IDGenerator;
import com.ibyte.sys.attach.constant.AttachLocationEnum;
import com.ibyte.sys.attach.constant.AttachStatusEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 附件基础信息
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Setter
@Getter
@MappedSuperclass
public abstract class SysAttachFileBase extends AbstractEntity implements FdCreateTime, FdLastModifiedTime {

    /**
     * 文件流的md5码，用作重复识别，秒传
     */
    @Column
    @Length(max = 32)
    @NotBlank
    private String fdMd5;

    /**
     * 文件大小（字节）
     */
    @Column
    @NotNull
    private Long fdFileSize;

    /**
     * 文件相对路径（以/开头，到文件名，一般以id为文件名且不存储扩展名）
     */
    @Column
    @Length(max = 255)
    @NotBlank
    private String fdFilePath;

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
     * 附件文件名（支持中文，不含扩展名，用于md5校验）
     */
    @Column
    @Length(max = 255)
    private String fdFileName;

    /**
     * 附件扩展名（不含.号，用于md5校验）
     */
    @Column
    @Length(max = 10)
    private String fdFileExtName;

    /**
     * 图片或视频宽度(像素)
     */
    @Column
    private Integer fdWidth;

    /**
     * 图片或视频高度(像素)
     */
    @Column
    private Integer fdHeight;

    /**
     * 文件状态
     */
    @Column
    @Convert(converter = AttachStatusEnum.Converter.class)
    private AttachStatusEnum fdStatus;

    /**
     * 文件存储位置（存储扩展点的id）
     */
    @Column
    @Length(max = 15)
    private String fdLocation;

    /**
     * 加密方式
     */
    @Column
    @Length(max = 10)
    private String fdEncryptMethod;

    /**
     * 创建人
     */
    @Column
    @Length(max = IDGenerator.LEN)
    private String fdCreatorId;

    /**
     * 修改人
     */
    @Column
    @Length(max = IDGenerator.LEN)
    private String fdLastModifier;

    /**
     * 获取文件存储的全路径
     */
    @Transient
    public String getFullPath() {
        StringBuilder pathBuilder = new StringBuilder();
        if (AttachLocationEnum.SERVER.getValue().equals(this.fdLocation)) {
            pathBuilder.append(this.fdSysAttachCatalog.getFdServerPath());
        }
        if (this.fdSysAttachModuleLocation != null) {
            pathBuilder.append(this.fdSysAttachModuleLocation.getFdModelPath());
        }
        pathBuilder.append(this.getFdFilePath());
        return pathBuilder.toString();
    }
}