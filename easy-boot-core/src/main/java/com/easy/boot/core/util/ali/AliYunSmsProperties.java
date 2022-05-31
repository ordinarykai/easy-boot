package com.easy.boot.core.util.ali;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * 阿里云短信配置类
 *
 * @author kai
 * @date 2022/3/12 14:48
 */
@Data
@ConfigurationProperties(prefix = "ali-yun.sms")
public class AliYunSmsProperties {

    /**
     * 阿里云短信accessKeyId
     */
    private String accessKeyId;
    /**
     * 阿里云短信accessKeySecret
     */
    private String accessKeySecret;


    private Map<String, AliYunSmsChannel> aliYunSmsChannelMap;

}
