package com.ibyte.sys.attach.dto;

import com.ibyte.sys.attach.constant.SysAttachConstant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 获取附件数据读取对象
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Getter
@Setter
@ToString
public class SysAttachFileReadVO {

    /**
     * 附件ID
     */
    private String fdId;

    /**
     * 读取起始位置
     */
    private Long offset;

    /**
     * 读取长度
     */
    private Long length;

    /**
     * 初始化附件读取对象
     */
    public static SysAttachFileReadVO of(String fdId) {
        SysAttachFileReadVO vo = new SysAttachFileReadVO();
        vo.setFdId(fdId);
        vo.setOffset(0L);
        vo.setLength(SysAttachConstant.FORCE_SLICE_FILE_SIZE);
        return vo;
    }
}
