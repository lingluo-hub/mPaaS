## 监控系统 
### SpringBoot Admin
| ![](https://images.gitee.com/uploads/images/2019/1128/203652_34bdd173_1468963.png "Springboot-admin1.png") | ![](https://images.gitee.com/uploads/images/2019/1128/203723_a461baf2_1468963.png "Springboot-admin2.png") |
| ---------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------- |

```java
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.ibyte.sys.monitor.core.config.AdminServerConfiguration
```
```java
//...
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
```
[Spring Boot Admin 装载配置](https://gitee.com/ibyte/M-Pass/blob/master/sys-manage/sys-monitor/sys-monitor-core/src/main/java/com/ibyte/sys/monitor/core/config/AdminServerConfiguration.java)