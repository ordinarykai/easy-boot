package com.easy.boot.core;

import com.easy.boot.core.auth.AuthInterceptor;
import com.easy.boot.core.auth.AuthProperties;
import com.easy.boot.core.constant.CommonConstant;
import com.easy.boot.core.version.VersionRequestMappingHandlerMapping;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;

/**
 * @author kai
 * @date 2022/3/12 14:56
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer, WebMvcRegistrations {

    @Resource
    private AuthProperties authProperties;
    @Resource
    private AuthInterceptor authInterceptor;
    @Resource
    private UploadProperties uploadProperties;
    /**
     * springboot配置文件当前环境
     */
    @Value("${spring.profiles.active}")
    private String env;
    /**
     * logback日志存储路径
     */
    @Value("${logback.path}")
    private String logPath;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册用户认证授权拦截器
        registry.addInterceptor(authInterceptor)
                .addPathPatterns(authProperties.getAddPathPatterns())
                .excludePathPatterns(authProperties.getExcludePathPatterns());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 解决跨域问题
        // springboot版本是2.5.8，设置allowedOrigins("*")且.allowCredentials(true)会报错
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("POST", "GET", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600);
    }

    /**
     * 配置静态资源的访问
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!env.equals(CommonConstant.PRO_ENV)) {
            // 内部权限编辑页面放在resources下的permission目录，生产环境严禁开启访问
            registry.addResourceHandler("/permission/**")
                    .addResourceLocations("classpath:/permission/");
            // 生产环境严禁开启远程日志访问
            registry.addResourceHandler("/logs/**")
                    .addResourceLocations("file:" + logPath);
        }
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:" + uploadProperties.getPath());
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

    /**
     * 版本控制
     */
    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new VersionRequestMappingHandlerMapping();
    }

}
