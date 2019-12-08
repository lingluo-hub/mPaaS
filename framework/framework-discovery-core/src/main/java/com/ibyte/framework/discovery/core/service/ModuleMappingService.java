package com.ibyte.framework.discovery.core.service;

import com.ibyte.framework.discovery.ModuleMappingHelper;
import com.ibyte.framework.discovery.dto.ModuleMappingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 获取应用信息
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@RestController
@RequestMapping("/api/framework-discovery/moduleMapping")
@Service
public class ModuleMappingService {
    /**
     * 返回信息key
     */
    private final static String APP_NAME = "appName";

    /**
     * 模块映射
     */
    @Autowired
    private ModuleMappingHelper mappingHelper;

    /**
     * 模块所属应用接口
     *
     * @param moduleName
     * @return
     */
    @PostMapping("get")
    public Map<String, String> get(@RequestBody String moduleName) {
        Map<String, String> rtnMap = new HashMap<String, String>(1);
        if (!StringUtils.isEmpty(moduleName)) {
            ModuleMappingInfo info = mappingHelper.getMappingInfo(moduleName);
            if (info != null) {
                rtnMap.put(APP_NAME, info.getApp());
            }
        }
        return rtnMap;
    }

}