package com.easy.boot.core.log.aop;

import com.easy.boot.core.auth.AuthUtil;
import com.easy.boot.core.log.WebLog;
import com.easy.boot.core.log.WebLogEvent;
import com.easy.boot.core.log.WebLogProperties;
import com.easy.boot.core.util.IpUtil;
import com.easy.boot.core.util.ServletUtil;
import com.easy.boot.core.util.bo.AuthInfo;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @author kai
 * @date 2022/3/12 13:24
 */
@Slf4j
@AllArgsConstructor
public class WebLogAspect implements MethodInterceptor {

    private final WebLogEvent webLogEvent;
    private final WebLogProperties webLogProperties;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (!webLogProperties.isEnable()) {
            return invocation.proceed();
        }
        //获取当前请求对象
        HttpServletRequest request = ServletUtil.getRequest();
        //记录请求信息
        WebLog webLog = new WebLog();
        Method method = invocation.getMethod();
        if (method.isAnnotationPresent(ApiOperation.class)) {
            ApiOperation log = method.getAnnotation(ApiOperation.class);
            webLog.setContents(log.value());
        }
        webLog.setIp(IpUtil.getIp(request));
        webLog.setMethod(request.getMethod());
        webLog.setParameter(this.getParameter(method, invocation.getArguments()));
        webLog.setUri(request.getRequestURI());
        webLog.setOperator(this.getOperator());
        // 打印请求信息
        log.info(webLog.toString());
        // 自定义日志事件
        webLogEvent.after(webLog);
        return invocation.proceed();
    }

    /**
     * 获取操作人ID
     */
    private String getOperator() {
        AuthInfo authInfo = AuthUtil.get();
        if (Objects.nonNull(authInfo)) {
            return authInfo.getId().toString();
        }
        return "";
    }

    /**
     * 根据方法和传入的参数获取请求参数
     */
    private Object getParameter(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            //将RequestBody注解修饰的参数作为请求参数
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            if (requestBody != null) {
                argList.add(args[i]);
            }
            //将RequestParam注解修饰的参数作为请求参数
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            if (requestParam != null) {
                Map<String, Object> map = new HashMap<>(1);
                String key = parameters[i].getName();
                if (StringUtils.hasText(requestParam.value())) {
                    key = requestParam.value();
                }
                map.put(key, args[i]);
                argList.add(map);
            }
        }
        if (argList.size() == 0) {
            return null;
        } else if (argList.size() == 1) {
            return argList.get(0);
        } else {
            return argList;
        }
    }

}
