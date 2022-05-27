package com.kai.boot.swagger;

import com.kai.boot.api.ResultCode;
import org.springframework.beans.factory.annotation.Value;
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

import static com.kai.boot.constant.CommonConstant.PRO_ENV;
import static com.kai.boot.constant.CommonConstant.TOKEN;
import static org.springframework.http.HttpMethod.*;

/**
 * Swagger配置类
 *
 * @author kai
 * @date 2022/3/12 14:02
 */
@Configuration
public class SwaggerConfig {

    /**
     * springboot配置文件当前环境
     */
    @Value("${spring.profiles.active}")
    private String env;

    /**
     * http://localhost:8080/swagger-ui.html#/
     */
    @Bean
    public Docket createApi() {
        return only200ResponseDocket()
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.kai.controller"))
                .paths(PathSelectors.any())
                .build()
                // Swagger生产环境严禁开启访问
                .enable(!env.equals(PRO_ENV))
                .securitySchemes(securitySchemes())
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
    protected ApiInfo apiInfo() {
        // 返回码描述
        String resultDescription = Arrays.stream(ResultCode.values())
                .map(resultCode -> resultCode.getCode() + ": " + resultCode.getMessage())
                .collect(Collectors.joining(", "));
        return new ApiInfoBuilder().title("接口文档")
                .description(resultDescription)
                .contact(new Contact("", "", ""))
                .version("1.0")
                .build();
    }

    /**
     * 为swagger 添加token框
     */
    protected List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> list = new ArrayList<>();
        list.add(new ApiKey(TOKEN, TOKEN, "header"));
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
