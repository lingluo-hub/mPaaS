package com.ibyte.attach.core.entity;

import com.ibyte.framework.meta.annotation.MetaEntity;
import com.ibyte.sys.attach.constant.AttachEffectTypeEnum;
import com.ibyte.sys.attach.constant.AttachStatusEnum;
import com.ibyte.sys.attach.constant.SysAttachConstant;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * 附件信息表
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Setter
@Getter
@Entity
@Table(name = "sys_attach_main", indexes = {@Index(columnList = "fd_attach_file_id"), @Index(columnList = "fdExpirationTime")})
@MetaEntity(displayProperty = "fdFileName")
public class SysAttachMain extends SysAttachBase {

    /**
     * SysAttachFile的主键（多对一，md5秒传会出现多对一）
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fd_attach_file_id")
    private SysAttachFile fdSysAttachFile;

    /**
     * 文件时效类型
     */
    @Column
    @Convert(converter = AttachEffectTypeEnum.Converter.class)
    private AttachEffectTypeEnum fdEffectType;

    /**
     * 附件状态
     */
    @Column
    @Convert(converter = AttachStatusEnum.Converter.class)
    private AttachStatusEnum fdStatus;

    /**
     * 过期时间（临时文件过期后系统会自动删除，但保留本表记录）
     */
    @Column
    private Timestamp fdExpirationTime;

    @Override
    public String getMechanismName() {
        return SysAttachConstant.SYS_ATTACH_MECHANISM_KEY;
    }
}

