package com.easy.boot.core.log.trace.id;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 日志链路traceId追踪
 *
 * @author kai
 */
@Configuration
public class TraceIdConfig {

    @Bean
    public TraceIdFilter traceIdFilter() {
        return new TraceIdFilter();
    }

}