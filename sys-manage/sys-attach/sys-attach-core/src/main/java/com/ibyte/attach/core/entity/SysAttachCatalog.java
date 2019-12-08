package com.ibyte.attach.core.entity;

import com.ibyte.common.core.data.field.FdCreateTime;
import com.ibyte.common.core.data.field.FdLastModifiedTime;
import com.ibyte.common.core.entity.AbstractEntity;
import com.ibyte.common.util.IDGenerator;
import com.ibyte.sys.attach.constant.AttachCatalogStatusEnum;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 附件模块目录信息-系统
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Setter
@Getter
@Entity
@Table(name = "sys_attach_catalog", indexes = {@Index(columnList = "fdServerPath")})
public class SysAttachCatalog extends AbstractEntity implements FdCreateTime, FdLastModifiedTime {

    /**
     * 文件存储的绝对路径头部（仅适用于本地文件存储方式）
     */
    @Column
    @Length(max = 255)
    private String fdServerPath;

    /**
     * 默认目录，有且只有一条记录
     */
    @Column
    private Boolean fdIsDefault;

    /**
     * 目录的存储限额（单位M）
     */
    @Column
    private Integer fdMaxStorage;

    /**
     * 预警阈值（百分比）
     */
    @Column
    private BigDecimal fdAlertPecent;

    /**
     * 目录状态
     */
    @Column
    @Convert(converter = AttachCatalogStatusEnum.Converter.class)
    private AttachCatalogStatusEnum fdStatus = AttachCatalogStatusEnum.VALID;

    /**
     * 创建人
     */
    @Column
    @Length(max = IDGenerator.LEN)
    private String  fdCreatorId;

    /**
     * 修改人
     */
    @Column
    @Length(max = IDGenerator.LEN)
    private String fdLastModifier;

}
