package com.easy.boot.core.auth;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kai
 * @date 2022/3/12 14:20
 */
@Configuration
@EnableConfigurationProperties(AuthProperties.class)
public class AuthConfig {

    @Bean
    @ConditionalOnMissingBean(AuthInterceptor.class)
    public AuthInterceptor authInterceptor(AuthProperties authProperties) {
        return new AuthInterceptor(authProperties);
    }

}
