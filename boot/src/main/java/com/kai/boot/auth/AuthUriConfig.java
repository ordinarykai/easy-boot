package com.kai.boot.auth;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 认证授权uri设置
 *
 * @author kai
 * @date 2022/3/12 14:20
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "auth")
public class AuthUriConfig {

    /**
     * 需要拦截的路径
     */
    private List<String> addPathPatterns = new ArrayList<>();
    /**
     * 拦截的路径中可以匿名访问的路径
     */
    private List<String> excludePathPatterns = new ArrayList<>();

}
