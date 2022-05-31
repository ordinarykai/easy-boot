package com.easy.boot.core.auth;

import com.alibaba.fastjson.JSONObject;
import com.easy.boot.core.api.ResultCode;
import com.easy.boot.core.util.bo.AuthInfo;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 用户认证授权拦截器
 *
 * @author kai
 * @date 2022/3/12 14:21
 */
@AllArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    /**
     * 认证相关配置
     */
    private final AuthProperties authProperties;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws IOException {
        String token = request.getHeader(authProperties.getTokenName());
        // token为空，认证失败
        if (StringUtils.isBlank(token)) {
            return failed(response, ResultCode.UNAUTHORIZED);
        }
        AuthInfo authInfo = AuthUtil.get(token);
        // token对应redis缓存无数据，认证失败
        if (authInfo == null) {
            return failed(response, ResultCode.UNAUTHORIZED);
        }
        // 判断登录用户是否有权限访问该路径
        PreAuthorize preAuthorize = handler.getClass().getAnnotation(PreAuthorize.class);
        if (preAuthorize != null && authInfo.getPermissions().contains(preAuthorize.value())) {
            return failed(response, ResultCode.FORBIDDEN);
        }
        return true;
    }

    /**
     * 认证授权失败，返回提示信息
     */
    private boolean failed(HttpServletResponse response, ResultCode resultCode) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        JSONObject res = new JSONObject();
        res.put("code", resultCode.getCode());
        res.put("message", resultCode.getMessage());
        PrintWriter out = response.getWriter();
        out.append(res.toString());
        return false;
    }

}
