package com.easy.boot.core.auth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 认证相关配置
 *
 * @author kai
 */
@Data
@ConfigurationProperties(prefix = "auth")
public class AuthProperties {
    /**
     * 需要拦截的路径
     */
    private List<String> addPathPatterns = new ArrayList<>();
    /**
     * 拦截的路径中可以匿名访问的路径
     */
    private List<String> excludePathPatterns = new ArrayList<>();
    /**
     * 用户登录过期时间，单位s，默认24小时
     */
    private long expireTime = 24 * 60 * 60;
    /**
     * header中token的名字
     */
    private String tokenName = "token";
    /**
     * redis中token key的前缀
     */
    private String tokenKeyPrefix = "token:";
}
