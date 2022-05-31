package com.easy.boot.core;

import cn.hutool.extra.spring.SpringUtil;
import com.easy.boot.core.api.GlobalExceptionHandler;
import com.easy.boot.core.version.VersionRequestMappingHandlerMapping;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author kai
 */
@Configuration
@EnableConfigurationProperties({UploadProperties.class})
public class BootConfig {

    @Bean
    public SpringUtil springUtil() {
        return new SpringUtil();
    }

    @Bean
    @ConditionalOnMissingBean(GlobalExceptionHandler.class)
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Bean
    public RequestMappingHandlerMapping versionRequestMappingHandlerMapping() {
        return new VersionRequestMappingHandlerMapping();
    }

}
