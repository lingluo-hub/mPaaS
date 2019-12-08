package com.ibyte.sys.attach.support;

import com.ibyte.framework.meta.Meta;
import com.ibyte.framework.meta.MetaEntity;
import com.ibyte.sys.attach.meta.SysAttachMeta;
import com.ibyte.sys.attach.meta.SysAttachMetas;

import java.util.HashMap;
import java.util.Map;

/**
 * 附件元数据工具类
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
public class SysAttachMetaUtil {

    private static ThreadLocal<Map<String, Map<String, SysAttachMeta>>> ATTACH_META_LOCAL = new ThreadLocal<>();

    /**
     * 根据entityName获取业务实体配置的附件元数据
     * @param entityName 业务实体类名
     * @return 以Map格式返回，key为entityKey，value为该entityKey对应的元数据。若返回NULL则表示未获取到MetaEntity
     */
    public static Map<String, SysAttachMeta> getAttachMetaMapByEntityName(String entityName) {
        if (ATTACH_META_LOCAL.get() == null) {
            ATTACH_META_LOCAL.set(new HashMap<>());
        }

        if (!ATTACH_META_LOCAL.get().containsKey(entityName)) {
            MetaEntity metaEntity = Meta.getEntity(entityName);
            if (metaEntity != null) {
                SysAttachMeta attachMetaUnit = metaEntity.getFeature(SysAttachMeta.class);
                if (attachMetaUnit != null) {
                    Map<String, SysAttachMeta> entityKeyMap = new HashMap<>();
                    entityKeyMap.put(attachMetaUnit.getFdEntityKey(), attachMetaUnit);
                    ATTACH_META_LOCAL.get().put(entityName, entityKeyMap);
                }

                SysAttachMetas sysAttachMetas = metaEntity.getFeature(SysAttachMetas.class);
                if (sysAttachMetas != null) {
                    Map<String, SysAttachMeta> entityKeyMap = new HashMap<>();
                    SysAttachMeta[] entities = sysAttachMetas.getValue();
                    for (SysAttachMeta sysAttachMeta : entities) {
                        entityKeyMap.put(sysAttachMeta.getFdEntityKey(), sysAttachMeta);
                    }
                    ATTACH_META_LOCAL.get().put(entityName, entityKeyMap);
                }
            }
        }

        if (!ATTACH_META_LOCAL.get().containsKey(entityName)) {
            ATTACH_META_LOCAL.get().put(entityName, null);
        }

        return ATTACH_META_LOCAL.get().get(entityName);
    }

    public static SysAttachMeta[] getAttachMetasByEntityName(String entityName) {
        MetaEntity metaEntity = Meta.getEntity(entityName);
        if (metaEntity != null) {
            SysAttachMeta sysAttachMeta = metaEntity.getFeature(SysAttachMeta.class);
            if (sysAttachMeta != null) {
                return new SysAttachMeta[]{sysAttachMeta};
            } else {
                SysAttachMetas sysAttachMetas = metaEntity.getFeature(SysAttachMetas.class);
                if (sysAttachMetas != null) {
                    return sysAttachMetas.getValue();
                }
            }
        }
        return null;
    }
}
