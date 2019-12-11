package com.ibyte.attach.core.api.operation.upload;

import com.ibyte.attach.core.api.store.SysAttachStoreService;
import com.ibyte.common.constant.NamingConstant;
import com.ibyte.common.core.util.MimeTypeUtil;
import com.ibyte.sys.attach.config.SysAttachConfig;
import com.ibyte.sys.attach.dto.SysAttachMediaSizeVO;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * 附件上传操作service基类
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
public class AbstractSysAttachUploadOperationService {

    @Autowired
    protected SysAttachConfig sysAttachConfig;

    @Autowired
    protected SysAttachStoreService sysAttachStoreService;

    /**
     * 对输入流进行加工，比如裁剪旋转
     * @return 加工后的io流
     */
    protected InputStream processStream(InputStream inputStream) {
        return inputStream;
    }

    /**
     * 获取媒体尺寸信息
     */
    protected SysAttachMediaSizeVO getMediaSize(String fileExtName, String filePath) {
        String mimeType = MimeTypeUtil.getMimeType(NamingConstant.DOT + fileExtName);
        if (mimeType.startsWith("image")) {
            InputStream imageStream = sysAttachStoreService.getStoreProxy().readFile(filePath, sysAttachConfig.getCurrentEncryMethod());
            return this.getImageSize(imageStream);
        }
        return null;
    }

    private SysAttachMediaSizeVO getImageSize(InputStream inputStream) {
        try {
            BufferedImage image = ImageIO.read(inputStream);
            if (image == null) {
                return null;
            }
            SysAttachMediaSizeVO mediaSizeVO = new SysAttachMediaSizeVO();
            mediaSizeVO.setWidth(image.getWidth());
            mediaSizeVO.setHeight(image.getHeight());
            return mediaSizeVO;
        } catch (IOException e) {
            return null;
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
}
