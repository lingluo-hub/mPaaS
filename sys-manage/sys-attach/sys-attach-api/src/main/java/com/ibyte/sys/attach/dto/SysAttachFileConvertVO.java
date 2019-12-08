package com.ibyte.sys.attach.dto;

import com.ibyte.common.core.dto.AbstractVO;
import com.ibyte.sys.attach.constant.AttachStatusEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 附件转换File对象
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Getter
@Setter
@ToString
public class SysAttachFileConvertVO extends AbstractVO {

    /**
     * 文件名
     */
    private String fdFileName;

    /**
     * 文件扩展名
     */
    private String fdFileExtName;

    /**
     * 文件相对路径（以/开头，到文件名，一般以id为文件名且不存储扩展名）
     */
    private String fdFilePath;

    /**
     * 文件全路，用于写文件
     */
    private String fullPath;

    /**
     * 文件流的md5码，用作重复识别，秒传
     */
    private String fdMd5;

    /**
     * 文件大小（字节）
     */
    private Long fdFileSize;

    /**
     * 文件状态
     */
    private AttachStatusEnum fdStatus;

    /**
     * 文档存储位置
     */
    private String fdLocation;

    /**
     * 加密方式
     */
    private String fdEncryptMethod;

    /**
     * 源文件fileId
     */
    private String fdOriginalAttachFile;

    /**
     * 文件转换类型(转换结果文件扩展名)
     */
    private String fdConvertType;

    /**
     * 附件转换器标识
     */
    private String fdConverterKey;

    /**
     * 图片文件转换后的像素，如512表示不超过512x512像素，width和height以大的为准
     */
    private Integer fdImagePix;

    /**
     * 图片文件按比例缩小，取值为0<fdImageScale<1，如果设置了fdImagePix，以fdImagePix为准
     */
    private Double fdImageScale;

    /**
     * 存储目录ID
     */
    private String fdSysAttachCatalog;

    /**
     * 模块目录ID
     */
    private String fdSysAttachModuleLocation;

    /**
     * 创建人
     */
    private String fdCreatorId;

    /**
     * 修改人
     */
    private String fdLastModifier;
}