package com.easy.boot.core.util.ali;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kai
 * @date 2022/3/12 14:48
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(AliYunSmsProperties.class)
public class AliYunSmsConfig {

    @Bean
    public AliYunSmsTemplate aliYunTemplate(AliYunSmsProperties aliYunSmsProperties){
        return new AliYunSmsTemplate(aliYunSmsProperties);
    }

}
