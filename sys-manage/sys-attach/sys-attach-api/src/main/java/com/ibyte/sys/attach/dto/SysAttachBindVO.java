package com.ibyte.sys.attach.dto;

import com.ibyte.sys.attach.dto.data.SysAttachFileData;

import java.util.List;

/**
 * 附件绑定对象
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
public class SysAttachBindVO {
    /**
     * 主文档ID
     */
    private String fdEntityId;

    /**
     * 主文档类别
     */
    private String fdEntityName;

    /**
     * 当前附件面板新增的附件列表
     */
    private List<SysAttachFileData> addedAttachFiles;

    /**
     * 当前附件面板删除的附件列表
     */
    private List<SysAttachFileData> deletedAttachFiles;
}
