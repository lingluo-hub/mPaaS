package com.ibyte.sys.attach.api;

import com.ibyte.sys.attach.dto.SysAttachImageProcessVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 图片处理Api
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
public interface ISysAttachImageProcessApi {
    /**
     * 裁剪图片
     * @return 裁剪后生成的文件ID
     */
    @PostMapping("/cut")
    String cutImage(@RequestBody SysAttachImageProcessVO imageCut);

    /**
     * 旋转图片
     * @return 旋转后生成的文件ID
     */
    @PostMapping("/rotate")
    String rotateImage(@RequestBody SysAttachImageProcessVO imageRotate);

}
