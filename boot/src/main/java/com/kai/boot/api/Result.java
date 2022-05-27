package com.kai.boot.api;

import com.kai.boot.api.exception.ApiException;

import static com.kai.boot.api.ResultCode.FAILED;
import static com.kai.boot.api.ResultCode.SUCCESS;

/**
 * 通用返回类
 *
 * @author kai
 * @date 2022/3/12 12:19
 */
public class Result<T> {

    /**
     * 状态码
     */
    private final int code;
    /**
     * 提示信息
     */
    private final String message;
    /**
     * 数据封装
     */
    private final T data;

    private Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回结果
     */
    public static Result<Void> success() {
        return new Result<>(SUCCESS.getCode(),
                SUCCESS.getMessage(),
                null);
    }

    /**
     * 成功返回结果
     *
     * @param data 返回数据
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(SUCCESS.getCode(),
                SUCCESS.getMessage(),
                data);
    }

    /**
     * 成功返回结果
     *
     * @param data    返回数据
     * @param message 提示信息
     */
    public static <T> Result<T> success(T data, String message) {
        return new Result<>(SUCCESS.getCode(),
                message,
                data);
    }

    /**
     * 失败返回结果
     */
    public static Result<Void> failed() {
        return new Result<>(FAILED.getCode(),
                FAILED.getMessage(),
                null);
    }

    /**
     * 失败返回结果
     *
     * @param message 提示信息
     */
    public static Result<Void> failed(String message) {
        return new Result<>(FAILED.getCode(),
                message,
                null);
    }

    /**
     * 失败返回结果
     *
     * @param apiException 自定义异常
     */
    public static Result<Void> failed(ApiException apiException) {
        return new Result<>(apiException.getCode(),
                apiException.getMessage(),
                null);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

}
