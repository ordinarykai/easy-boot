package com.easy.boot.core.api.exception;

import com.easy.boot.core.api.ResultCode;

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
