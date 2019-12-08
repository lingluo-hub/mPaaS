package com.ibyte.framework.discovery.client.interceptor;

import com.ibyte.framework.discovery.DiscoveryHeaderHelper;
import feign.RequestInterceptor;
import feign.RequestTemplate;

import java.util.Map;

/**
 * feign远程调用请求增加头部信息处理
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
public class ApiFeignInterceptor implements RequestInterceptor {

    /**
     * 统一处理feign的远程调用拦截
     *
     * @param requestTemplate
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        for (Map.Entry<String, String> entry : DiscoveryHeaderHelper.getInstance().getRequestHeaderInfo().entrySet()) {
            requestTemplate.header(entry.getKey(), entry.getValue());
        }
    }
}
