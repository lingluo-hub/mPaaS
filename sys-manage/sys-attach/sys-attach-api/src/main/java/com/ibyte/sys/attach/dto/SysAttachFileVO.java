package com.ibyte.sys.attach.dto;

import com.ibyte.common.core.dto.AbstractVO;
import com.ibyte.sys.attach.constant.AttachLocationEnum;
import com.ibyte.sys.attach.constant.AttachStatusEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 附件File对象
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Getter
@Setter
@ToString
public class SysAttachFileVO extends AbstractVO {

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
     * 文件流的md5码，用作重复识别，秒传
     */
    private String fdMd5;

    /**
     * 文件大小（字节）
     */
    private Long fdFileSize;

    /**
     * 图片或视频宽度(像素)
     */
    private Integer fdWidth;

    /**
     * 图片或视频高度(像素)
     */
    private Integer fdHeight;

    /**
     * 文件状态
     */
    private AttachStatusEnum fdStatus;

    /**
     * 一级目录
     */
    private SysAttachCatalogVO fdSysAttachCatalog;

    /**
     * 模块目录
     */
    private SysAttachModuleLocationVO fdSysAttachModuleLocation;

    /**
     * 文档存储位置
     */
    private String fdLocation;

    /**
     * 加密方式
     */
    private String fdEncryptMethod;

    /**
     * 创建人
     */
    private String fdCreatorId;

    /**
     * 修改人
     */
    private String fdLastModifier;

    /**
     * 获取文件存储的全路径
     */
    public String getFullPath() {
        StringBuilder pathBuilder = new StringBuilder();
        if (AttachLocationEnum.SERVER.getValue().equals(this.fdLocation)) {
            pathBuilder.append(this.fdSysAttachCatalog.getFdServerPath());
        }
        if (this.fdSysAttachModuleLocation != null) {
            pathBuilder.append(this.fdSysAttachModuleLocation.getFdModelPath());
        }
        pathBuilder.append(this.getFdFilePath());
        return pathBuilder.toString();
    }
}
