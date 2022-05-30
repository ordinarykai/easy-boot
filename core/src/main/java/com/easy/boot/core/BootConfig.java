package com.easy.boot.core;

import cn.hutool.extra.spring.SpringUtil;
import com.easy.boot.core.api.GlobalExceptionHandler;
import com.easy.boot.core.log.WebLogAspect;
import com.easy.boot.core.log.WebLogEvent;
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
    @ConditionalOnMissingBean(WebLogEvent.class)
    public WebLogEvent webLogEvent() {
        return new WebLogEvent();
    }

    @Bean
    @ConditionalOnMissingBean(WebLogAspect.class)
    public WebLogAspect webLogAspect(WebLogEvent webLogEvent) {
        return new WebLogAspect(webLogEvent);
    }

    @Bean
    public RequestMappingHandlerMapping versionRequestMappingHandlerMapping() {
        return new VersionRequestMappingHandlerMapping();
    }

}
