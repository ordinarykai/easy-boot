package com.easy.boot.core.swagger;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import springfox.documentation.service.Contact;

/**
 * Swagger配置类
 *
 * @author kai
 * @date 2022/3/12 14:02
 */
@Data
@ConfigurationProperties(prefix = "swagger")
@ConditionalOnProperty(prefix = "swagger", name = {"enable"}, havingValue = "true", matchIfMissing = true)
public class SwaggerProperties {

    private boolean enable = false;

    private String basePackage;

    private String title = "接口文档";

    private String version = "1.0.0";

    private Contact contact = new Contact("kai", "https://www.yuque.com/ordinarykai", "2115114903@qq.com");

    private String tokenName = "token";

}
