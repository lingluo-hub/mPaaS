package com.ibyte.sys.attach.dto.slice;

import com.ibyte.sys.attach.dto.base.SysAttachUploadBaseVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Setter
@Getter
@ToString
public abstract class SysAttachUploadSliceBaseVO extends SysAttachUploadBaseVO {

    /**
     * 是否是顺序上传
     * <P/>必传
     */
    protected boolean fdOrderUpload;

    /**
     * 当前分片序号（从0开始）
     * <P/>必传
     */
    protected Integer fdSliceIndex;

    /**
     * 总分片数
     * <P/>必传
     */
    protected Integer fdTotalSlices;

    /**
     * 每个分片大小
     * <P/>必传
     */
    protected Long fdEachSliceSize;
}
