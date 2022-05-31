package com.easy.boot.core.api;

/**
 * 返回码枚举
 *
 * @author kai
 * @date 2022/3/12 12:19
 */
public enum ResultCode {

    /**
     * 成功
     */
    SUCCESS(200, "Success"),
    /**
     * 失败
     */
    FAILED(400, "Failed Request"),
    /**
     * 未认证
     */
    UNAUTHORIZED(401, "Unauthorized"),
    /**
     * 未授权
     */
    FORBIDDEN(403, "Forbidden");

    /**
     * 返回码
     */
    private final int code;
    /**
     * 返回消息
     */
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
