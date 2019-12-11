package com.ibyte.attach.core.entity;

import com.ibyte.common.core.data.field.FdCreateTime;
import com.ibyte.common.core.data.field.FdLastModifiedTime;
import com.ibyte.common.core.data.field.FdOrder;
import com.ibyte.common.core.data.field.MechanismData;
import com.ibyte.common.core.entity.AbstractEntity;
import com.ibyte.common.util.IDGenerator;
import com.ibyte.sys.attach.constant.AttachTypeEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * 附件基础信息-基类
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Setter
@Getter
@MappedSuperclass
public abstract class SysAttachBase extends AbstractEntity implements FdOrder, FdCreateTime, FdLastModifiedTime,
        MechanismData {

    /**
     * 附件文件名（支持中文，不含扩展名）
     */
    @Column
    @Length(max = 255)
    private String fdFileName;

    /**
     * 附件扩展名（不含.）
     */
    @Column
    @Length(max = 10)
    private String fdFileExtName;

    /**
     * 文件大小（字节）
     */
    @Column
    @NotNull
    private Long fdFileSize;

    /**
     * 文件的mime类型
     */
    @Column
    @Length(max = 100)
    private String fdMimeType;

    /**
     * 附件类型
     */
    @Column
    @Convert(converter = AttachTypeEnum.Converter.class)
    private AttachTypeEnum fdAttachType;

    /**
     * 附件扩展信息
     */
    @Column
    @Length(max = 2000)
    private String fdExtendInfo;

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
     * 是否可以匿名访问
     */
    @Column
    protected Boolean fdAnonymous = false;
}