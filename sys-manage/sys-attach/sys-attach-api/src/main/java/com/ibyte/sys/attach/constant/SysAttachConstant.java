package com.ibyte.sys.attach.constant;

/**
 * 附件机制常量
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
public interface SysAttachConstant {

    /**
     * 实体机制数据中，附件机制数据的KEY
     */
    String SYS_ATTACH_MECHANISM_KEY = "attachment";

    /**
     * 默认的存储实现
     */
    String SYS_ATTACH_DEFAULT_STORE = "sys.attach.store.currentStoreLocation";

    /**
     * 默认的加密方式
     */
    String SYS_ATTACH_DEFAULT_ENCRY = "sys.attach.store.encry.currentEncryMethod";

    /**
     * 强制分片文件大小(20MB)
     */
    long FORCE_SLICE_FILE_SIZE = 20L* 1024 * 1024;
}
