package com.ibyte.sys.attach.support.store;

/**
 * 附件存储直连接口
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
public interface ISysAttachStoreDirect {

    /**
     * 下载链接
     *
     * @param fdPath
     * @return
     * @
     */
    String getDownloadUrl(String fdPath, String fileName);

    /**
     * 删除链接
     *
     * @param fdPath
     *
     * @return
     * @
     */
    String getDeleteUrl(String fdPath);

    /**
     * 上传链接
     * @param userAgent 浏览器标识
     * @return
     * @
     */
    String getUploadUrl(String userAgent) ;

    /**
     * 上传method类型，默认post
     *
     * @return
     */
    String getMethodName();

    /**
     * 文件上传file域的name 例如阿里云使用的是“file”<br>
     * 此值为空代表传输实体部分是一个无结构二级制数据，适合PUT方法
     *
     * @return
     */
    String getFileVal();

    /**
     * 是否支持直连<br>
     * @param userAgent 浏览器标识
     * @return
     */
    boolean supportDirect(String userAgent);

}
