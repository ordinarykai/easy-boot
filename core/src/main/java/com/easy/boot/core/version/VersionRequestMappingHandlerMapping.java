package com.easy.boot.core.version;


import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author kai
 */
public class VersionRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = super.getMappingForMethod(method, handlerType);
        ApiVersion apiVersion = handlerType.getAnnotation(ApiVersion.class);
        if (Objects.isNull(apiVersion)) {
            return info;
        }
        String versionUrl = "/v" + apiVersion.value();
        if (Objects.isNull(info)) {
            return null;
        }
        return RequestMappingInfo.paths(versionUrl).build().combine(info);
    }

}
