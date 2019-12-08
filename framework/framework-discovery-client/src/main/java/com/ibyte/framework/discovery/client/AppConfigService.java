package com.ibyte.framework.discovery.client;

import com.ibyte.common.constant.NamingConstant;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 应用配置刷新触发
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Slf4j
@Component
public class AppConfigService implements ApplicationListener<ApplicationReadyEvent> {
    /**
     * redis
     */
    @Autowired
    private RedissonClient redissonClient;

    /**
     * 刷新器
     */
    @Autowired
    private ContextRefresher contextRefresher;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        redissonClient.getTopic(NamingConstant.TOPIC_APP_CONFIG_REFRESH).addListener(String.class,
                new MessageListener<String>() {
                    @Override
                    public void onMessage(CharSequence channel, String msg) {
                        contextRefresher.refresh();
                        log.info("业务模块配置刷新完毕");
                    }
                });
    }

}
