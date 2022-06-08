package com.easy.boot.core.log;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 接口请求日志配置
 *
 * @author kai
 */
@Data
@ConfigurationProperties(prefix = "web-log")
@ConditionalOnProperty(prefix = "web-log", name = {"enable"}, havingValue = "true", matchIfMissing = true)
public class WebLogProperties {

    /**
     * 是否开启
     */
    private boolean enable = true;

    /**
     * 日志切面pointcut
     */
    private String pointcut = "@annotation(org.springframework.web.bind.annotation.RequestMapping) " +
            "||@annotation(org.springframework.web.bind.annotation.PostMapping) " +
            "||@annotation(org.springframework.web.bind.annotation.GetMapping) " +
            "||@annotation(org.springframework.web.bind.annotation.DeleteMapping) " +
            "||@annotation(org.springframework.web.bind.annotation.PutMapping) ";

}
