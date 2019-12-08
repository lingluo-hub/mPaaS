package com.ibyte.sys.attach.support.store;

import java.io.InputStream;
import java.util.Map;

/**
 * 附件存储流代理接口
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
public interface ISysAttachStoreProxy {

    /**
     * 写入文件流，新建文件
     * @param inputSream 文件输入流
     * @param filePath 包含扩展名（可以没有）的文件路径，用来确定文件的唯一位置，可直接用作OSS的对象名
     */
    void writeFile(InputStream inputSream, String filePath);

    /**
     * 写入文件流，新建文件
     * @param inputSream 文件输入流
     * @param filePath 包含扩展名（可以没有）的文件路径，用来确定文件的唯一位置，可直接用作OSS的对象名
     * @param header 文件http头
     */
    void writeFile(InputStream inputSream, String filePath, Map<String,String> header);
}
