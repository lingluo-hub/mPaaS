package com.ibyte.sys.attach.support.encry;

import java.io.InputStream;

/**
 * 附件加解密扩展点必须实现的接口
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
public interface ISysAttachEncry {

    /**
     * 初始化加密流
     * @param originStream
     * @return
     */
    InputStream initEncryInputStream(InputStream originStream);

    /**
     * 初始化解密流
     * @param originStream
     * @return
     */
    InputStream initDecryInputStream(InputStream originStream);
}
