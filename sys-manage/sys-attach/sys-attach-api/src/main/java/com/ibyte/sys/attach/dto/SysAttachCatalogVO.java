package com.ibyte.sys.attach.dto;

import com.ibyte.common.core.data.field.FdCreateTime;
import com.ibyte.common.core.data.field.FdLastModifiedTime;
import com.ibyte.common.core.dto.AbstractVO;
import com.ibyte.sys.attach.constant.AttachCatalogStatusEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 一级目录VO
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Getter
@Setter
@ToString
public class SysAttachCatalogVO extends AbstractVO implements FdCreateTime, FdLastModifiedTime {


    private String fdId;

    /**
     * 文件存储的绝对路径头部（仅适用于本地文件存储方式）
     */
    private String fdServerPath;

    /**
     * 默认目录，有且只有一条记录（此记录的fdEntityName应为空）
     */
    private Boolean fdIsDefault;

    /**
     * 目录的存储限额（单位M）
     */
    private Integer fdMaxStorage;

    /**
     * 预警阈值（百分比）
     */
    private BigDecimal fdAlertPecent;

    /**
     * 目录状态
     */
    private AttachCatalogStatusEnum fdStatus;

    /**
     * 创建人
     */
    private String  fdCreatorId;

    /**
     * 修改人
     */
    private String fdLastModifier;
}
