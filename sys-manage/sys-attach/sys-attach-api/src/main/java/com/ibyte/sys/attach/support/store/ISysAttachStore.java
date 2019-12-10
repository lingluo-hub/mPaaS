package com.ibyte.sys.attach.support.store;

/**
 * 附件存储扩展点必须实现的接口
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
public interface ISysAttachStore {

    /**
     * 获取直连service（不支持直连的存储扩展返回null）
     * @return
     */
    default ISysAttachStoreDirect getDirectService(){
        return null;
    }

    /**
     * 获取流代理service
     * @return
     */
    ISysAttachStoreProxy getProxyService();

}
