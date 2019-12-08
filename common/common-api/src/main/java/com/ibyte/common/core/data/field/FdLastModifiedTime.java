package com.ibyte.common.core.data.field;

import com.ibyte.common.core.data.IData;
import com.ibyte.common.core.data.handler.FieldHandler;
import com.ibyte.common.core.data.handler.FieldHandlerExtension;
import com.ibyte.framework.meta.MetaConstant.ShowType;
import com.ibyte.framework.meta.annotation.MetaProperty;

import javax.persistence.Column;
import javax.persistence.Index;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * 最后修改时间字段
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Table(indexes = @Index(columnList = "fdLastModifiedTime"))
public interface FdLastModifiedTime extends IData, IField {
    /**
     * 读-最后修改时间
     *
     * @return
     */
    @Column
    @MetaProperty(showType = ShowType.NONE, readOnly = true, messageKey = "property.fdLastModifiedTime")
    default Timestamp getFdLastModifiedTime() {
        return (Timestamp) getExtendProps().get("fdLastModifiedTime");
    }

    /**
     * 写-最后修改时间
     *
     * @param fdLastModifiedTime
     */
    default void setFdLastModifiedTime(Timestamp fdLastModifiedTime) {
        getExtendProps().put("fdLastModifiedTime", fdLastModifiedTime);
    }

    /**
     * 修改时间更新
     *
     * @author 叶中奇
     */
    @FieldHandlerExtension
    public class FdLastModifiedTimeHandler implements FieldHandler {
        @Override
        public boolean support(IData entity) {
            return entity instanceof FdLastModifiedTime;
        }

        @Override
        public void beforeSaveOrUpdate(IData entity, boolean isAdd) {
            ((FdLastModifiedTime) entity).setFdLastModifiedTime(new Timestamp(System.currentTimeMillis()));
        }

    }
}

