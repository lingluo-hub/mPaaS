package com.ibyte.sys.attach.support;

import com.ibyte.common.exception.ParamsNotValidException;
import com.ibyte.sys.attach.meta.SysAttachMeta;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 附件上传校验
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Service
public class SysAttachUploadChecker {
    public static void checkUpload(String entityName, String entityKey, Long fileSize, String fileExtName) {
        if (entityKey == null) {
            // 未传入entityKey的情况下，无法进行校验，暂且允许上传
            return;
        }

        Map<String, SysAttachMeta> metaMap = SysAttachMetaUtil.getAttachMetaMapByEntityName(entityName);
        if (metaMap == null) {
            // 未获取到MetaEntity信息，直接返回
            return;
        }

        if (!metaMap.containsKey(entityKey)) {
            throw new ParamsNotValidException("未定义的附件entityKey，请确认业务实体是否有定义附件机制元数据");
        }

        SysAttachMeta sysAttachMeta = metaMap.get(entityKey);
        long singleMaxSize = sysAttachMeta.getSingleMaxSize();
        String[] enableFileType = sysAttachMeta.getEnableFileType();
        String[] disableFileType = sysAttachMeta.getDisableFileType();

        if (fileSize > singleMaxSize * 1024 * 1024) {
            throw new ParamsNotValidException("单次上传文件大小不允许超过" + singleMaxSize + "MB");
        }

        if (enableFileType.length > 0) {
            Set<String> enableFileTypeSet = new HashSet<>(Arrays.asList(enableFileType));
            if (!enableFileTypeSet.contains(fileExtName)) {
                throw new ParamsNotValidException(sysAttachMeta.getFdEntityKey() + "附件面板不允许上传" + fileExtName +
                        "格式文件");
            }
        }

        if (disableFileType.length > 0) {
            Set<String> disableFileTypeSet = new HashSet<>(Arrays.asList(disableFileType));
            if (disableFileTypeSet.contains(fileExtName)) {
                throw new ParamsNotValidException(sysAttachMeta.getFdEntityKey() + "附件面板不允许上传" + fileExtName +
                        "格式文件");
            }
        }
    }
}