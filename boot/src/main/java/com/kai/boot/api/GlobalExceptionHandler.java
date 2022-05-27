package com.kai.boot.api;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.kai.boot.api.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Objects;

import static com.kai.boot.constant.CommonConstant.DEFAULT_EXCEPTION_MSG;
import static com.kai.boot.constant.CommonConstant.PRO_ENV;

/**
 * 全局异常处理
 *
 * @author kai
 * @date 2022/3/12 12:55
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * springboot配置文件当前环境
     */
    @Value("${spring.profiles.active}")
    private String env;

    @ExceptionHandler(value = Exception.class)
    public Result<Void> exceptionHandler(Exception exception) {
        log.error("系统异常", exception);
        return Result.failed(PRO_ENV.equals(env) ? DEFAULT_EXCEPTION_MSG : exception.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    private Result<Void> nullPointerExceptionHandler(Exception exception) {
        StackTraceElement element = exception.getStackTrace()[0];
        String message = String.format("空指针异常：%s.%s at line %s)",
                element.getClassName(), element.getMethodName(), element.getLineNumber());
        log.error("空指针异常", exception);
        return Result.failed(PRO_ENV.equals(env) ? DEFAULT_EXCEPTION_MSG : message);
    }

    @ExceptionHandler(value = ApiException.class)
    public Result<Void> apiExceptionHandler(ApiException exception) {
        log.error("自定义异常", exception);
        return Result.failed(exception);
    }

    /**
     * 捕获Get请求中使用@Valid验证路径中请求实体校验失败后抛出的异常
     */
    @ExceptionHandler(BindException.class)
    public Result<Void> validExceptionHandler(BindException exception) {
        log.warn("数据校验异常", exception);
        BindingResult result = exception.getBindingResult();
        FieldError fieldError = result.getFieldError();
        if (Objects.nonNull(fieldError)) {
            return Result.failed(fieldError.getDefaultMessage());
        }
        return Result.failed("数据格式校验错误");
    }

    /**
     * 捕获@RequestBody上validate失败后抛出的异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> validExceptionHandler(MethodArgumentNotValidException exception) {
        log.warn("数据校验异常", exception);
        BindingResult result = exception.getBindingResult();
        FieldError fieldError = result.getFieldError();
        if (Objects.nonNull(fieldError)) {
            return Result.failed(fieldError.getDefaultMessage());
        }
        ObjectError globalError = result.getGlobalError();
        if (Objects.nonNull(globalError)) {
            return Result.failed(globalError.getDefaultMessage());
        }
        return Result.failed("数据格式校验错误");
    }

    /**
     * 捕获请求数据转换抛出的异常
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<Void> validJson(HttpMessageNotReadableException exception) {
        log.warn("数据校验异常", exception);
        String message = "请求数据有误";
        Throwable throwable = exception.getCause();
        try {
            // 字段不匹配
            if (exception.getRootCause() instanceof MismatchedInputException
                    || throwable instanceof MismatchedInputException) {
                MismatchedInputException cause = (MismatchedInputException) throwable;
                String name = createName(cause.getPath());
                Class<?> type = cause.getTargetType();
                message = createMessage(throwable, name, type);
            }
            // json格式不正确
            else if (throwable instanceof JsonParseException) {
                JsonParseException cause = (JsonParseException) throwable;
                String name = cause.getProcessor().getCurrentName();
                message = "json格式错误，靠近" + name + "字段";
            }
            // json格式不正确
            else if (throwable instanceof JsonMappingException) {
                JsonMappingException cause = (JsonMappingException) throwable;
                List<JsonMappingException.Reference> path = cause.getPath();
                String name = createName(path);
                message = "json格式错误，靠近" + name + "字段";
            }
        } catch (Exception otherException) {
            log.warn("数据校验异常", otherException);
        }
        return Result.failed(message);
    }

    /**
     * 构造由【MismatchedInputException】异常产生的错误消息
     *
     * @param throwable 异常
     * @param name      产生异常的字段名称
     * @param type      产生异常的字段类型
     */
    private String createMessage(Throwable throwable, String name, Class<?> type) {
        // 字段格式不匹配
        if (throwable instanceof InvalidFormatException) {
            InvalidFormatException cause2 = (InvalidFormatException) throwable;
            Object value = cause2.getValue();
            return "字段" + name
                    + "输入格式有误，无法将【" + value + "】转换至【"
                    + type.getSimpleName() + "】类型";
        }
        // 字段类型不匹配
        return "字段" + name
                + "类型不匹配，请传入【"
                + type.getSimpleName() + "】类型";

    }

    /**
     * 构造产生异常的字段名称
     *
     * @param path 字段反序列化链，包含字段的信息
     */
    private String createName(List<JsonMappingException.Reference> path) {
        // 直接返回本身的字段名称（a.b[0].c格式）
        StringBuilder name = new StringBuilder();
        path.forEach(reference -> {
            String fieldName = reference.getFieldName();
            if (Objects.nonNull(fieldName)) {
                name.append(fieldName).append(".");
            } else {
                int index = reference.getIndex();
                int len = name.length();
                name.replace(len - 1, len, "")
                        .append("[").append(index).append("]").append(".");
            }
        });
        int len = name.length();
        return name.replace(len - 1, len, "").toString();
    }

}
