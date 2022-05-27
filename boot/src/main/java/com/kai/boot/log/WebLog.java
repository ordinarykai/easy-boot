package com.kai.boot.log;

import lombok.Data;

/**
 * web请求日志实体
 *
 * @author kai
 * @date 2022/3/12 13:24
 */
@Data
public class WebLog {

    /**
     * IP地址
     */
    private String ip;

    /**
     * URI
     */
    private String uri;

    /**
     * 请求类型
     */
    private String method;

    /**
     * 请求参数
     */
    private Object parameter;

    /**
     * 操作用户ID
     */
    private String operator;

    /**
     * 操作描述
     */
    private String contents;

    @Override
    public String toString() {
        return "\r\n------------------------------------------------------------------------------\r\n" +
                "ip:" + ip + "\r\n" +
                "uri:" + uri + "\r\n" +
                "method:" + method + "\r\n" +
                "parameter:" + parameter + "\r\n" +
                "operator:" + operator + "\r\n" +
                "------------------------------------------------------------------------------";
    }

}
