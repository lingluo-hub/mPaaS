package com.ibyte.sys.attach.api;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
public interface ISysAttachExtensionPointApi {
    /**
     *存储位置
     */
    @RequestMapping(value = "location")
    default String getLocation() {
        return "server";
    }


}
