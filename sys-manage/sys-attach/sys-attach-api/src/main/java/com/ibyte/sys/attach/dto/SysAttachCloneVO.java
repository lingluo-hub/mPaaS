package com.ibyte.sys.attach.dto;

import com.ibyte.common.core.dto.MechanismDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 附件克隆对象
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Getter
@Setter
@ToString
public class SysAttachCloneVO {

    /**
     * 原始主文档ID
     */
    private MechanismDTO sourceAttachModelVO;

    // 原始类型

    // 原始key

    /**
     * 克隆新生成的主文档ID
     */
    private MechanismDTO destAttachModelVO;

}