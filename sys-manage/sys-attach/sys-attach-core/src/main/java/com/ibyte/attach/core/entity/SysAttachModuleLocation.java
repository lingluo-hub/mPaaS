package com.ibyte.attach.core.entity;

import com.ibyte.common.core.data.field.FdCreateTime;
import com.ibyte.common.core.data.field.FdLastModifiedTime;
import com.ibyte.common.core.entity.AbstractEntity;
import com.ibyte.common.util.IDGenerator;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 模块存储路径
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Setter
@Getter
@Entity
@Table(name = "sys_attach_module_location", indexes = @Index(columnList = "fdEntityName"))
public class SysAttachModuleLocation extends AbstractEntity implements FdCreateTime, FdLastModifiedTime {

    /**
     * 附件主文档类名
     */
    @Column
    @Length(max = 255)
    private String fdEntityName;

    /**
     * 附件主文档模块名
     * <P/>中文显示名
     */
    @Column
    @Length(max = 255)
    private String fdModelName;

    /**
     * 此模块附件存放位置的相对路径（以/开始，仅存放一级目录）
     */
    @Column
    @Length(max = 100)
    private String fdModelPath;

    /**
     * 是否是当前有效的目录
     */
    @Column
    private Boolean fdIsCurrent;

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

}
