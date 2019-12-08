package com.ibyte.framework.discovery.client;

import com.ibyte.framework.discovery.api.ISystemConfigApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * 系统配置远程调用接口
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@FeignClient(name = "${kmss.svr.framework-discovery.app}", path = "/api/framework-discovery/systemConfig")
public interface SystemConfigClient extends ISystemConfigApi {
}
