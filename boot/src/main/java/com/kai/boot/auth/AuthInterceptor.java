package com.kai.boot.auth;

import com.alibaba.fastjson.JSONObject;
import com.kai.boot.api.ResultCode;
import com.kai.boot.util.LoginUtil;
import com.kai.boot.util.bo.LoginInfo;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.kai.boot.constant.CommonConstant.TOKEN;

/**
 * 用户认证授权拦截器
 *
 * @author kai
 * @date 2022/3/12 14:21
 */
@AllArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final LoginUtil loginUtil;
    /**
     * 路径匹配器
     */
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws IOException {
        String uri = request.getRequestURI();
        String token = request.getHeader(TOKEN);
        // token为空，认证失败
        if (StringUtils.isBlank(token)) {
            return failed(response, ResultCode.UNAUTHORIZED);
        }
        LoginInfo loginInfo = loginUtil.get(token);
        // token对应redis缓存无数据，认证失败
        if (loginInfo == null) {
            return failed(response, ResultCode.UNAUTHORIZED);
        }
        // 判断登录用户是否有权限访问该路径
        List<String> allPermissions = getAllPermissions();
        if (allPermissions.contains(uri) && !hasPermissions(new ArrayList<>(), uri)) {
            return failed(response, ResultCode.FORBIDDEN);
        }
        return true;
    }

    public Set<String> getPresentUserPermissions() {
        return Collections.emptySet();
    }

    public List<String> getAllPermissions() {
        return Collections.emptyList();
    }

    /**
     * 判断登录用户是否有权限访问该uri
     *
     * @param roleIds 登录用户角色id集合
     * @param uri     请求uri
     * @return true：有权限，false：没有授权
     */
    private boolean hasPermissions(List<Integer> roleIds, String uri) {
        Set<String> permissions = getPresentUserPermissions();
        return permissions.stream().anyMatch(pattern -> antPathMatcher.match(pattern, uri));
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
