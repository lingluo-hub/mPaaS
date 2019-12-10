package com.ibyte.sys.attach.annotation;

import com.ibyte.framework.plugin.annotation.LocalExtensionPoint;
import com.ibyte.sys.attach.support.encry.ISysAttachEncry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 附件加解密扩展点注解
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@LocalExtensionPoint(label = "附件加解密扩展", baseOn = ISysAttachEncry.class)
public @interface SysAttachEncry {

    /**
     * 加密方式，存储于file表的fdEncryMethod字段中
     */
    String id();

    /**
     * 加密方式名称
     */
    String messageKey();

}