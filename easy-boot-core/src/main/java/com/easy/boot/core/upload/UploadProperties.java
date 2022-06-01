package com.easy.boot.core.upload;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 文件上传配置
 * @author kai
 * @date 2022/3/12 14:34
 */
@Data
@ConfigurationProperties(prefix = "upload")
public class UploadProperties {

    /**
     * 上传文件存储目录
     */
    private String path;

    /**
     * 文件读取地址
     */
    private String url;

}
