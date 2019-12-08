package com.ibyte.sys.attach.support.convert;

import com.ibyte.framework.plugin.Plugin;
import com.ibyte.sys.attach.meta.SysAttachConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 附件转换器工厂
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Component
public class SysAttachConverterFactory {

    /**
     * 根据源文件和目标文件扩展名获取附件转换器
     */
    public ISysAttachConverter getAttachConverter(String sourceFileExtName,String targetFileExtName) {
        List<SysAttachConverter> providerList = Plugin.getProviders(SysAttachConverter.class);
        for (Iterator<SysAttachConverter> iterator = providerList.iterator(); iterator.hasNext();) {
            SysAttachConverter sysAttachConverter = iterator.next();
            if(sourceFileExtName.equalsIgnoreCase(sysAttachConverter.getSourceFileExtName())
                    && targetFileExtName.equalsIgnoreCase(sysAttachConverter.getTargetFileExtName())) {
                return sysAttachConverter.getSysAttachConverter();
            }
        }
        return null;
    }

    /**
     * 根据源文件扩展名获取附件转换器列表
     */
    public List<ISysAttachConverter> getAttachConverters(String sourceFileExtName) {
        List<ISysAttachConverter> converterList = new ArrayList<>();
        List<SysAttachConverter> providerList = Plugin.getProviders(SysAttachConverter.class);
        for (Iterator<SysAttachConverter> iterator = providerList.iterator(); iterator.hasNext();) {
            SysAttachConverter sysAttachConverter = iterator.next();
            if(sourceFileExtName.equalsIgnoreCase(sysAttachConverter.getSourceFileExtName())) {
                converterList.add(sysAttachConverter.getSysAttachConverter());
            }
        }
        return converterList;
    }
}
