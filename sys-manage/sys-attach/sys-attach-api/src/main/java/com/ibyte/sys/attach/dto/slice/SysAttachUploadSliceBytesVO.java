package com.ibyte.sys.attach.dto.slice;

import lombok.*;

/**
 * 分片上传对象（API上传）
 * 文件分片上传对象
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"fdFileData"})
public class SysAttachUploadSliceBytesVO extends SysAttachUploadSliceBaseVO {

    /**
     * 文件二进制内容
     */
    protected byte[] fdFileData;

    /*
    主文档信息
     */
    /**
     * 主文档ID
     */
    protected String fdEntityId;

    /**
     * 附件key
     */
    protected String fdEntityKey;
}
