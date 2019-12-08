package com.ibyte.sys.attach.dto;

import com.ibyte.common.core.data.field.FdCreateTime;
import com.ibyte.common.core.data.field.FdLastModifiedTime;
import com.ibyte.common.core.dto.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 模块目录
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Getter
@Setter
@ToString
public class SysAttachModuleLocationVO extends AbstractVO implements FdCreateTime, FdLastModifiedTime {

    /**
     * 附件主文档模块名
     */
    private String fdModelName;

    /**
     * 附件主文档类名
     */
    private String fdEntityName;

    /**
     * 此模块附件存放位置的相对路径（以/开始，仅存放一级目录）
     */
    private String fdModelPath;

    /**
     * 是否是当前有效的目录
     */
    private Boolean fdIsCurrent;

    /**
     * 创建人
     */
    private String  fdCreatorId;

    /**
     * 修改人
     */
    private String fdLastModifier;

}
