package com.ibyte.sys.attach.dto.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 附件上传VO基类
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Getter
@Setter
@ToString
public abstract class SysAttachUploadBaseVO {

    /**
     * 原始附件ID
     * <P/>如果是导入附件，则需要传入此参数
     * <P/>普通上传不需要传入
     * <P/>非必传
     */
    protected String originAttachId;

    /**
     * 附件文件名（支持中文，不含扩展名）
     * <P/>必传
     */
    protected String fdFileName;

    /**
     * 附件扩展名（不含.号）
     * <P/>必传
     */
    protected String fdFileExtName;

    /**
     * 原始文件大小
     * <P/>必传
     */
    protected Long fdFileSize;

    /**
     * 原始文件MD5值
     * <P/>必传
     */
    protected String fdMd5;

    /**
     * 文件上传人
     * <P/>必传
     */
    protected String fdCreatorId;


    /**
     * 主文档类别
     * <P/>必传，用于确定上传到哪个模块目录
     */
    protected String fdEntityName;

    /**
     * 附件扩展信息
     * <P/>非必传
     */
    private String fdExtendInfo;

    /**
     * 附件是否可以匿名访问
     * <P/>非必传，不传表示不允许匿名访问
     */
    protected Boolean fdAnonymous = false;
}