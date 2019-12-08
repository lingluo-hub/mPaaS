package com.ibyte.sys.attach.api;

/**
 * 附件机制实体接口
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
public interface ISysAttach {
    /**
     * 存储位置
     */
    default String getEntityClass() {
        return this.getClass().getCanonicalName();
    }

}