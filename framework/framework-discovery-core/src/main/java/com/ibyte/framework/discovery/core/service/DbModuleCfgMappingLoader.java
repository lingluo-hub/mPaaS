package com.ibyte.framework.discovery.core.service;

import com.ibyte.framework.discovery.ModuleMappingLoader;
import com.ibyte.framework.discovery.dto.ModuleMappingInfo;
import com.ibyte.framework.discovery.dto.SystemConfigVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 从数据库配置取配置信息
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Service
@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE - 1)
@Transactional(readOnly = true, rollbackFor = {})
public class DbModuleCfgMappingLoader implements ModuleMappingLoader {

    /**
     * 系统配置
     */
    @Autowired
    private SystemConfigService configService;

    /**
     * 加载映射配置
     *
     * @return
     */
    @Override
    public Map<String, ModuleMappingInfo> loadMapping() {
        Map<String, ModuleMappingInfo> rtnMap = new HashMap<>(1);
        List<SystemConfigVo> mappingConfigs = configService.findAll(null);
        if (!CollectionUtils.isEmpty(mappingConfigs)) {
            mappingConfigs.forEach((configVo) -> {
                String key = configVo.getFdKey();
                String value = configVo.getFdValue();
                buildMapingInfo(rtnMap, key, value);
            });

        }
        return rtnMap;
    }
}

