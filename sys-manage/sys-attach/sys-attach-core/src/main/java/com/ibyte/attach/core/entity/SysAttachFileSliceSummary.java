package com.ibyte.attach.core.entity;

import com.ibyte.common.core.data.field.FdCreateTime;
import com.ibyte.common.core.data.field.FdLastModifiedTime;
import com.ibyte.common.core.data.field.MechanismData;
import com.ibyte.common.core.entity.AbstractEntity;
import com.ibyte.common.util.IDGenerator;
import com.ibyte.sys.attach.constant.SysAttachConstant;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 文件分片概要信息
 * <P/>记录原始文件信息
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Setter
@Getter
@Entity
@Table(name = "sys_attach_file_slice_Summary", indexes = @Index(columnList = "fdMd5, fdFileSize, fdFileName, fdFileExtName"))
public class SysAttachFileSliceSummary extends AbstractEntity implements FdCreateTime, FdLastModifiedTime, MechanismData {

    /**
     * 总分片数
     */
    @Column
    @NotNull
    private Integer fdTotalSlices;

    /**
     * 源文件大小（字节）
     */
    @Column
    @NotNull
    private Long fdFileSize;

    /**
     * 每个分片大小
     */
    @Column
    @NotNull
    private Long fdEachSliceSize;

    /**
     * 是否顺序上传
     */
    @Column
    @NotNull
    private Boolean fdOrderUpload;

    /**
     * 源文件流的md5码，用作重复识别，秒传
     */
    @Column
    @Length(max = 32)
    @NotBlank
    private String fdMd5;

    /**
     * 源附件文件名（支持中文，不含扩展名，用于md5校验）
     */
    @Column
    @Length(max = 255)
    @NotBlank
    private String fdFileName;

    /**
     * 源附件扩展名（不含.号，用于md5校验）
     */
    @Column
    @Length(max = 10)
    @NotBlank
    private String fdFileExtName;

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

    @Override
    public String getMechanismName() {
        return SysAttachConstant.SYS_ATTACH_MECHANISM_KEY;
    }
}
