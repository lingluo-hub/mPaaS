package com.ibyte.common.core.data.field;

import com.ibyte.common.core.data.IData;
import com.ibyte.framework.meta.annotation.MetaProperty;

import javax.persistence.Column;

/**
 * 排序号字段
 *
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
public interface FdOrder extends IData, IField {
    /**
     * 读-排序号
     *
     * @return
     */
    @Column
    @MetaProperty(messageKey = "property.fdOrder")
    default Integer getFdOrder() {
        return (Integer) getExtendProps().get("fdOrder");
    }

    /**
     * 写-排序号
     *
     * @param fdOrder
     */
    default void setFdOrder(Integer fdOrder) {
        getExtendProps().put("fdOrder", fdOrder);
    }
}