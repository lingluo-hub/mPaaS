package com.ibyte.sys.attach.dto;

import com.ibyte.sys.attach.dto.base.SysAttachUploadBaseVO;
import lombok.*;

/**
 * 附件上传对象（API上传）
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"fdFileData"})
public class SysAttachUploadBytesVO extends SysAttachUploadBaseVO {

    /**
     * 文件二进制内容
     * <P/>必传
     */
    protected byte[] fdFileData;

    /*
    主文档信息
     */
    /**
     * 主文档ID
     * <P/>临时附件可不传此参数
     */
    protected String fdEntityId;

    /**
     * 附件key
     * <P/>临时附件可不传此参数
     */
    protected String fdEntityKey;
}