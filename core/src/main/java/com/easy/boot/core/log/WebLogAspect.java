package com.easy.boot.core.log;

import com.easy.boot.core.auth.AuthUtil;
import com.easy.boot.core.util.IpUtil;
import com.easy.boot.core.util.ServletUtil;
import com.easy.boot.core.util.bo.AuthInfo;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * 日志切面
 *
 * @author kai
 * @date 2022/3/12 13:24
 */
@Slf4j
@Aspect
@AllArgsConstructor
public class WebLogAspect {

    /**
     * 是否打印切面日志
     */
    private static final boolean IS_PRINT_LOG = true;
    private final WebLogEvent webLogEvent;

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping) " +
            "||@annotation(org.springframework.web.bind.annotation.PostMapping) " +
            "||@annotation(org.springframework.web.bind.annotation.GetMapping) " +
            "||@annotation(org.springframework.web.bind.annotation.DeleteMapping) " +
            "||@annotation(org.springframework.web.bind.annotation.PutMapping) ")
    public void webLog() {
    }

    @Around("webLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取当前请求对象
        HttpServletRequest request = ServletUtil.getRequest();
        //记录请求信息
        WebLog webLog = new WebLog();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method.isAnnotationPresent(ApiOperation.class)) {
            ApiOperation log = method.getAnnotation(ApiOperation.class);
            webLog.setContents(log.value());
        }
        webLog.setIp(IpUtil.getIp(request));
        webLog.setMethod(request.getMethod());
        webLog.setParameter(this.getParameter(method, joinPoint.getArgs()));
        webLog.setUri(request.getRequestURI());
        webLog.setOperator(this.getOperator());
        if (IS_PRINT_LOG) {
            // 打印请求信息
            log.info(webLog.toString());
        }
        webLogEvent.savaDatabase(webLog);
        return joinPoint.proceed();
    }

    /**
     * 获取操作人ID
     */
    private String getOperator() {
        AuthInfo authInfo = AuthUtil.get();
        if(Objects.nonNull(authInfo)){
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