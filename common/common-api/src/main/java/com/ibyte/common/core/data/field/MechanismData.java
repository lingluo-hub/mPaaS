package com.ibyte.common.core.data.field;

import com.ibyte.common.core.data.IData;
import com.ibyte.common.util.IDGenerator;
import com.ibyte.framework.meta.annotation.MetaProperty;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 机制类数据，含fdEntityName, fdEntityId, fdEntityKey
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Table(indexes = @Index(columnList = "fdEntityName, fdEntityId, fdEntityKey"))
public interface MechanismData extends IData, IField {
    /**
     * 读-EntityName
     *
     * @return
     */
    @Length(max = 100)
    @MetaProperty(messageKey = "property.fdEntityName")
    default String getFdEntityName() {
        return (String) getExtendProps().get("fdEntityName");
    }

    /**
     * 写-EntityName
     *
     * @param fdEntityName
     */
    default void setFdEntityName(String fdEntityName) {
        getExtendProps().put("fdEntityName", fdEntityName);
    }

    /**
     * 读-fdEntityId
     *
     * @return
     */
    @Length(max = IDGenerator.LEN)
    @MetaProperty(messageKey = "property.fdEntityId")
    default String getFdEntityId() {
        return (String) getExtendProps().get("fdEntityId");
    }

    /**
     * 写-fdEntityId
     *
     * @param fdEntityId
     */
    default void setFdEntityId(String fdEntityId) {
        getExtendProps().put("fdEntityId", fdEntityId);
    }

    /**
     * 读-fdEntityKey
     *
     * @return
     */
    @Length(max = 200)
    @MetaProperty(messageKey = "property.fdEntityKey")
    default String getFdEntityKey() {
        return (String) getExtendProps().get("fdEntityKey");
    }

    /**
     * 写-fdEntityKey
     *
     * @param fdEntityKey
     */
    default void setFdEntityKey(String fdEntityKey) {
        getExtendProps().put("fdEntityKey", fdEntityKey);
    }

    /**
     * 获取机制名称在mechanisms 中的 key，用于表示该Model是属于哪个机制的
     * @return
     */
    String getMechanismName();
}