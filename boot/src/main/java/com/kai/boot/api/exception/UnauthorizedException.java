package com.kai.boot.api.exception;

import com.kai.boot.api.ResultCode;

/**
 * 未认证异常
 *
 * @author kai
 * @date 2022/3/12 12:28
 */
public class UnauthorizedException extends ApiException {

    public UnauthorizedException() {
        super(ResultCode.UNAUTHORIZED);
    }

    public UnauthorizedException(String message) {
        super(ResultCode.UNAUTHORIZED, message);
    }

}
