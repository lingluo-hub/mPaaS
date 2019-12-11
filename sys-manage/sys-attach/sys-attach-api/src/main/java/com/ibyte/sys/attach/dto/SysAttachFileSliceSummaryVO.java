package com.ibyte.sys.attach.dto;

import com.ibyte.common.core.dto.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 文件分片概要信息
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Setter
@Getter
@ToString
public class SysAttachFileSliceSummaryVO extends AbstractVO {

    /**
     * 总分片数
     */
    private Integer fdTotalSlices;

    /**
     * 每个分片大小
     */
    private Long fdEachSliceSize;

    /**
     * 源文件大小（字节）
     * <P/>必传
     */
    private Long fdFileSize;

    /**
     * 是否顺序上传
     * <P/>必传
     */
    private Boolean fdOrderUpload;

    /**
     * 源文件流的md5码，用于支持秒传
     * <P/>可不传，不传则无法支持秒传
     */
    private String fdMd5;

    /**
     * 源附件文件名（不含扩展名）
     * <P/>必传
     */
    private String fdFileName;

    /**
     * 源附件扩展名（不含.号）
     * <P/>必传
     */
    private String fdFileExtName;

    /**
     * 主文档ID
     * <P/>必传
     */
    private String fdEntityId;

    /**
     * 主文档类别
     * <P/>必传
     */
    private String fdEntityName;

    /**
     * 附件key
     * <P/>必传
     */
    private String fdEntityKey;

    /**
     * 创建人
     */
    private String fdCreatorId;

    /**
     * 修改人
     */
    private String fdLastModifier;

    /**
     * 创建时间
     */
    private Date fdCreateTime;
}
