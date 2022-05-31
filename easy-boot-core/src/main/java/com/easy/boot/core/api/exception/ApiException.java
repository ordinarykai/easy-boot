package com.easy.boot.core.api.exception;

import com.easy.boot.core.api.ResultCode;

/**
 * 自定义异常
 *
 * @author kai
 * @date 2022/3/12 12:18
 */
public class ApiException extends RuntimeException {

    /**
     * 异常状态枚举
     */
    private final int code;

    protected ApiException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }

    protected ApiException(ResultCode resultCode, String message) {
        super(message);
        this.code = resultCode.getCode();
    }

    public ApiException(String message) {
        super(message);
        this.code = ResultCode.FAILED.getCode();
    }

    public int getCode() {
        return code;
    }

}
