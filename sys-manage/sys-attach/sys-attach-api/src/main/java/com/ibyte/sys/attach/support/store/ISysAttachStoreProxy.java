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


    /**
     * 拼接存储全路径
     * @param catalog 一级目录
     * @param modelPath 模块目录
     * @param filePath 文件相对路径
     * @return 存储全路径
     */
    String buildFullPath(String catalog, String modelPath, String filePath);


    /**
     * 读取文件流
     * @param filePath 包含扩展名（可以没有）的文件路径，用来确定文件的唯一位置，可直接用作OSS的对象名
     * @param encryMethod 加密方式
     * @return inputSream 文件输入流
     */
    InputStream readFile(String filePath,String encryMethod);
}
