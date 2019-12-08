package com.ibyte.framework.discovery.core.service;

import com.ibyte.framework.discovery.api.ISystemConfigApi;
import com.ibyte.framework.discovery.constant.SystemConfigConstant;
import com.ibyte.framework.discovery.core.entity.SystemConfig;
import com.ibyte.framework.discovery.core.repository.SystemConfigRepository;
import com.ibyte.framework.discovery.dto.SystemConfigVo;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 系统信息配置服务
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@RestController
@RequestMapping("/api/framework-discovery/systemConfig")
@Service
@Transactional(readOnly = true, rollbackFor = {})
public class SystemConfigService implements ISystemConfigApi, SystemConfigConstant {

    @Autowired
    private SystemConfigRepository systemConfigRepository;

    @Transactional(rollbackFor = {})
    @Override
    public void saveAll(List<SystemConfigVo> systemConfigVoList) {
        for (SystemConfigVo configVo : systemConfigVoList) {
            systemConfigRepository.save(voToEntity(configVo));
        }
    }

    @Transactional(rollbackFor = {})
    @Override
    public void delete(String key) {
        systemConfigRepository.deleteById(key);
    }

    @Transactional(rollbackFor = {})
    @Override
    public void deleteAll(String[] keys) {
        for (String key : keys) {
            systemConfigRepository.deleteById(key);
        }
    }

    @Transactional(rollbackFor = {})
    @Override
    public void clear(String configPre) {
        if (!StringUtils.isEmpty(configPre)) {
            systemConfigRepository.findAll().forEach((config) -> {
                if (config.getFdId().startsWith(configPre)) {
                    systemConfigRepository.delete(config);
                }
            });
        }
    }

    @Override
    public List<SystemConfigVo> findAll(String[] keys) {
        List<SystemConfigVo> systemConfigVos = new ArrayList<SystemConfigVo>(1);
        Iterator<SystemConfig> iterator = null;
        if (ArrayUtils.isEmpty(keys)) {
            iterator = systemConfigRepository.findAll().iterator();
        } else {
            iterator = systemConfigRepository.findAllById(Arrays.asList(keys)).iterator();
        }
        while (iterator.hasNext()) {
            systemConfigVos.add(entityToVo(iterator.next()));
        }
        return systemConfigVos;
    }

    @Override
    public SystemConfigVo findOne(String key) {
        Optional<SystemConfig> rtnObj = systemConfigRepository.findById(key);
        SystemConfigVo systemConfigVo = null;
        if (rtnObj.isPresent()) {
            systemConfigVo = entityToVo(rtnObj.get());
        }
        return systemConfigVo;
    }

    private SystemConfig voToEntity(SystemConfigVo systemConfigVo) {
        SystemConfig systemConfig = null;
        if (systemConfigVo != null) {
            systemConfig = new SystemConfig();
            systemConfig.setFdId(systemConfigVo.getFdKey());
            systemConfig.setFdValue(systemConfigVo.getFdValue());
            if (StringUtils.isEmpty(systemConfig.getFdLabel())) {
                systemConfig.setFdLabel(CONFIG_LABEL);
            }
            if (StringUtils.isEmpty(systemConfig.getFdApplication())) {
                systemConfig.setFdApplication(CONFIG_APPLICATION);
            }
            if (StringUtils.isEmpty(systemConfig.getFdProfile())) {
                systemConfig.setFdProfile(CONFIG_PROFILE);
            }
        }
        return systemConfig;
    }

    private SystemConfigVo entityToVo(SystemConfig systemConfig) {
        SystemConfigVo systemConfigVo = null;
        if (systemConfig != null) {
            systemConfigVo = new SystemConfigVo(systemConfig.getFdId(), systemConfig.getFdValue());
        }
        return systemConfigVo;
    }
}
