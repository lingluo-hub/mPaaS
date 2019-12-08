package com.ibyte.framework.discovery.client;
import com.ibyte.common.constant.NamingConstant;
import com.ibyte.framework.discovery.client.feign.FeignErrorDecoder;
import com.ibyte.framework.discovery.client.interceptor.ApiFeignInterceptor;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 服务注册客户端配置类
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Configuration
@EnableDiscoveryClient
@EnableFeignClients(basePackages = NamingConstant.BASE_PACKAGE)
@EnableCircuitBreaker
public class DiscoveryClientConfig {

    /**
     * feign请求拦截器
     *
     * @return
     */
    @Bean
    public RequestInterceptor feignInterceptor() {
        return new ApiFeignInterceptor();
    }

    /**
     * feign 异常处理
     * @return
     */
    @Bean
    public ErrorDecoder feignErrorDecoder(){
        return new FeignErrorDecoder();
    }

}
