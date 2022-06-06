package com.easy.boot.core;

import com.easy.boot.core.auth.AuthInterceptor;
import com.easy.boot.core.auth.AuthProperties;
import com.easy.boot.core.upload.UploadProperties;
import com.easy.boot.core.version.VersionRequestMappingHandlerMapping;
import org.apache.commons.lang3.StringUtils;
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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册用户认证授权拦截器
        if(authProperties.isEnable()){
            registry.addInterceptor(authInterceptor)
                    .addPathPatterns(authProperties.getAddPathPatterns())
                    .excludePathPatterns(authProperties.getExcludePathPatterns());
        }
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
     * 配置上传文件静态资源的访问
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (StringUtils.isNotBlank(uploadProperties.getPath())) {
            registry.addResourceHandler("/upload/**")
                    .addResourceLocations("file:" + uploadProperties.getPath());
        }
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
