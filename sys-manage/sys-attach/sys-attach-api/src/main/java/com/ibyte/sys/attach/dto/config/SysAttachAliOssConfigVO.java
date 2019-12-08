package com.ibyte.sys.attach.dto.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author <a href="mailto:shangzhi.ibyte@gmail.com">iByte</a>
 * @since 1.0.1
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SysAttachAliOssConfigVO {

    /**
     * 阿里云存储端点域名
     */
    private String endpoint;

    /**
     * 阿里云存储卷标
     */
    private String bucket;

    /**
     * 阿里云存储授权ID
     */
    private String accessKeyId;

    /**
     * 阿里云存储授权密钥
     */
    private String accessKeySecret;
}
