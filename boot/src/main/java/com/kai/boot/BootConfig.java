package com.kai.boot;

import com.kai.boot.auth.AuthInterceptor;
import com.kai.boot.redis.service.RedisService;
import com.kai.boot.util.LoginUtil;
import com.kai.boot.version.VersionRequestMappingHandlerMapping;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author kai
 */
@Configuration
public class BootConfig {

    @Bean
    public LoginUtil loginUtil(RedisService redisService) {
        return new LoginUtil(redisService);
    }

    @Bean
    public RequestMappingHandlerMapping versionRequestMappingHandlerMapping() {
        return new VersionRequestMappingHandlerMapping();
    }

    @Bean
    @ConditionalOnMissingBean(AuthInterceptor.class)
    public AuthInterceptor authInterceptor(LoginUtil loginUtil) {
        return new AuthInterceptor(loginUtil);
    }

}
