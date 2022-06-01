package com.easy.boot.core.swagger;

import com.easy.boot.core.api.ResultCode;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.easy.boot.core.constant.Constants.TOKEN;
import static org.springframework.http.HttpMethod.*;

/**
 * Swagger配置类
 *
 * @author kai
 * @date 2022/3/12 14:02
 */
@Configuration
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerConfig {

    /**
     * http://localhost:8080/swagger-ui/index.html
     */
    @Bean
    public Docket createApi(SwaggerProperties swaggerProperties) {
        return only200ResponseDocket()
                .apiInfo(apiInfo(swaggerProperties))
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                .paths(PathSelectors.any())
                .build()
                // Swagger生产环境严禁开启访问
                .enable(swaggerProperties.isEnable())
                .securitySchemes(securitySchemes(swaggerProperties))
                .securityContexts(securityContexts());
    }

    /**
     * 只显示200响应
     */
    private Docket only200ResponseDocket() {
        return new Docket(DocumentationType.OAS_30)
                .globalResponses(GET, new ArrayList<>())
                .globalResponses(POST, new ArrayList<>())
                .globalResponses(PUT, new ArrayList<>())
                .globalResponses(DELETE, new ArrayList<>());
    }

    /**
     * 默认标题信息
     */
    @Bean
    public ApiInfo apiInfo(SwaggerProperties swaggerProperties) {
        // 返回码描述
        String resultDescription = Arrays.stream(ResultCode.values())
                .map(resultCode -> resultCode.getCode() + ": " + resultCode.getMessage())
                .collect(Collectors.joining(", "));
        return new ApiInfoBuilder().title(swaggerProperties.getTitle())
                .description(resultDescription)
                .contact(swaggerProperties.getContact())
                .version(swaggerProperties.getVersion())
                .build();
    }

    /**
     * 为 swagger 添加 token 框
     */
    @Bean
    protected List<SecurityScheme> securitySchemes(SwaggerProperties swaggerProperties) {
        List<SecurityScheme> list = new ArrayList<>();
        list.add(new ApiKey(swaggerProperties.getTokenName(), swaggerProperties.getTokenName(), "header"));
        return list;
    }

    protected List<SecurityContext> securityContexts() {
        List<SecurityContext> list = new ArrayList<>();
        list.add(SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build());
        return list;
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> list = new ArrayList<>();
        list.add(new SecurityReference(TOKEN, authorizationScopes));
        return list;
    }

}
