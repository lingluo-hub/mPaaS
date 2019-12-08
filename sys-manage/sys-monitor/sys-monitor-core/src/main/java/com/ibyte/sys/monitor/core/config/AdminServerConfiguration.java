package com.ibyte.sys.monitor.core.config;

import com.ibyte.framework.discovery.DiscoveryHeaderHelper;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.web.client.CompositeHttpHeadersProvider;
import de.codecentric.boot.admin.server.web.client.HttpHeadersProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.*;

/**
 * Spring Boot Admin 装载配置
 *
 * @link  resource.. spring.factories
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@EnableAdminServer
@Configuration
public class AdminServerConfiguration {
    /**
     * 自定义http头
     *
     * @param delegates
     * @return
     */
    @Bean
    @Primary
    public CompositeHttpHeadersProvider httpHeadersProvider(Collection<HttpHeadersProvider> delegates) {
        return new CustomHttpHeadersProvider(delegates);
    }

    /**
     * 增加自定义认证头
     */
    class CustomHttpHeadersProvider extends CompositeHttpHeadersProvider {
        CustomHttpHeadersProvider(Collection<HttpHeadersProvider> delegates) {
            super(delegates);
        }

        @Override
        public HttpHeaders getHeaders(Instance instance) {
            HttpHeaders headers = super.getHeaders(instance);
            Map<String, List<String>> headerMap = new HashMap<>(1);
            DiscoveryHeaderHelper.getInstance().getRequestHeaderInfo().forEach((key, value) -> {
                headerMap.put(key, Collections.singletonList(value));
            });
            headers.putAll(headerMap);
            return headers;
        }
    }

}



