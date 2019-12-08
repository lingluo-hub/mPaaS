package com.ibyte.sys.attach.dto;

import lombok.*;

/**
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"fdFileData"})
public class SysAttachFileDataVO {

    /** 文件字节数据 */
    private byte[] fdFileData;

    /** 原始文件总大小 */
    private Integer fdFileSize;

    /** 文件全名 */
    private String fdFullName;
}
