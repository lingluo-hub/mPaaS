package com.ibyte.sys.attach.meta;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 接收具体的附件面板配置
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Setter
@Getter
@ToString
public class SysAttachMeta {

    /**
     * 主文档类名
     */
    private String fdEntityName;

    /**
     * 附件标识
     */
    private String fdEntityKey;

    /**
     * 附件面板名称
     */
    private String messageKey;

    /**
     * 是否允许匿名访问
     */
    private boolean fdAnonymous;

    /**
     * 附件列表显示的附件信息（如：标题，大小等）
     */
    private String[] displayInfo;

    /**
     * 允许上传的文件类型
     */
    private String[] enableFileType;

    /**
     * 禁止上传的文件类型
     */
    private String[] disableFileType;

    /**
     * 是否必填/不为空（默认必填）
     */
    private boolean required;

    /**
     * 是否多文件上传（默认多文件上传）
     */
    private boolean multi;

    /**
     * 附件类型
     */
    private String attachType;

    /**
     * 单个附件最大大小，单位MB
     */
    private long singleMaxSize;

    /**
     * 附件面板所有附件最大大小，单位MB
     */
    private long totalMaxSize;

    /**
     * 是否允许上传（不允许则无上传按钮）
     */
    private boolean enableUpload;

    /**
     * 是否允许下载（不允许则无下载按钮）
     */
    private boolean enableDownload;

    /**
     * 是否允许删除（不允许则无删除按钮）
     */
    private boolean enableDelete;

    /**
     * 是否允许编辑（不允许则无编辑按钮，仅对office类型有效）
     */
    private boolean enableEdit;

    /**
     * 是否允许打印（不允许则无打印按钮）
     */
    private boolean enablePrint;
}