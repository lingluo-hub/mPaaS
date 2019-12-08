package com.ibyte.common.core.data.field;

import com.ibyte.common.core.data.IData;
import com.ibyte.common.core.data.handler.FieldHandler;
import com.ibyte.common.core.data.handler.FieldHandlerExtension;
import com.ibyte.framework.meta.annotation.MetaProperty;

import javax.persistence.Column;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Date;


/**
 * 创建时间字段
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Table(indexes = @Index(columnList = "fdCreateTime"))
public interface FdCreateTime extends IData, IField {
    /**
     * 读-创建时间
     *
     * @return
     */
    @Column
    @MetaProperty(readOnly = true, messageKey = "property.fdCreateTime")
    default Date getFdCreateTime() {
        return (Date) getExtendProps().get("fdCreateTime");
    }

    /**
     * 写-创建时间
     *
     * @param fdCreateTime
     */
    default void setFdCreateTime(Date fdCreateTime) {
        getExtendProps().put("fdCreateTime", fdCreateTime);
    }

    /**
     * 创建时间初始化
     *
     * @author 叶中奇
     */
    @FieldHandlerExtension
    public class FdCreateTimeHandler implements FieldHandler {
        @Override
        public boolean support(IData entity) {
            return entity instanceof FdCreateTime;
        }

        @Override
        public void doInit(IData data) {
            FdCreateTime entity = (FdCreateTime) data;
            if (entity.getFdCreateTime() == null) {
                entity.setFdCreateTime(new Date());
            }
        }
    }
}