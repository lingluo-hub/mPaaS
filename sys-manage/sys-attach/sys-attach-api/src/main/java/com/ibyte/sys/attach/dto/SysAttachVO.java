package com.ibyte.sys.attach.dto;

import com.ibyte.common.core.dto.AbstractVO;
import com.ibyte.sys.attach.constant.AttachTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Convert;

/**
 * 附件基本信息对象
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Getter
@Setter
@ToString
public class SysAttachVO extends AbstractVO {

    /**
     * 附件主文档ID
     */
    private String fdEntityId;

    /**
     * 附件主文档类型
     */
    private String fdEntityName;

    /**
     * 附件key
     */
    private String fdEntityKey;

    /**
     * 文件名
     */
    private String fdFileName;

    /**
     * 文件扩展名
     */
    private String fdFileExtName;

    /**
     * 文件大小
     */
    private Long fdFileSize;

    /**
     * 文件的mime类型
     */
    private String fdMimeType;

    /**
     * 附件类型
     */
    @Convert(converter = AttachTypeEnum.Converter.class)
    private AttachTypeEnum fdAttachType;

    /**
     * 附件扩展信息
     */
    private String fdExtendInfo;

    /**
     * 图片或视频宽度(像素)
     */
    private Integer fdWidth;

    /**
     * 图片或视频高度(像素)
     */
    private Integer fdHeight;

    /**
     * SysAttachFile的主键（多对一）
     */
    private String fdSysAttachFile;

    /**
     * 文件上传人
     */
    private String fdCreatorId;

    /**
     * 修改人
     */
    private String fdLastModifier;

    /**
     * 附件下载链接
     */
    private String downloadUrl;

    /**
     * 是否可以匿名访问
     */
    protected Boolean fdAnonymous;
}
