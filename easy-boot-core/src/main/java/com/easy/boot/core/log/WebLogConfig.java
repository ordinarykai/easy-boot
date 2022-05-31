package com.easy.boot.core.log;

import com.easy.boot.core.log.aop.WebLogAspect;
import com.easy.boot.core.log.aop.WebLogPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * 接口请求自动配置类
 *
 * @author kai
 */
@Configuration
@EnableConfigurationProperties(WebLogProperties.class)
public class WebLogConfig {

    @Bean
    @ConditionalOnMissingBean(WebLogEvent.class)
    public WebLogEvent webLogEvent() {
        return new WebLogEvent();
    }

    @Bean
    @ConditionalOnMissingBean(WebLogAspect.class)
    public WebLogAspect webLogAspect(WebLogEvent webLogEvent, WebLogProperties webLogProperties) {
        return new WebLogAspect(webLogEvent, webLogProperties);
    }

    @Bean
    public WebLogPointcutAdvisor webLogPointcutAdvisor(WebLogAspect webLogAspect, WebLogProperties webLogProperties) {
        WebLogPointcutAdvisor advisor = new WebLogPointcutAdvisor(webLogProperties.getPointcut());
        advisor.setAdvice(webLogAspect);
        advisor.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return advisor;
    }

}
