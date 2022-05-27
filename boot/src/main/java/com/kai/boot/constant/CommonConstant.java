package com.kai.boot.constant;

/**
 * 系统常量
 *
 * @author kai
 * @date 2022/3/12 13:49
 */
public interface CommonConstant {

    /**
     * header中的token标志
     */
    String TOKEN = "token";

    /**
     * 默认密码
     */
    String DEFAULT_PWD = "123456";

    /**
     * 用户登录过期时间，单位s，默认24小时
     */
    long EXPIRE_TIME = 24 * 60 * 60;

    /**
     * java.time.LocalTime格式化文本
     */
    String LOCAL_TIME_PATTERN = "HH:mm:ss";

    /**
     * java.time.LocalDate格式化文本
     */
    String LOCAL_DATE_PATTERN = "yyyy-MM-dd";

    /**
     * java.time.LocalDateTime格式化文本
     */
    String LOCAL_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 正式环境标识符
     */
    String PRO_ENV = "pro";

    /**
     * 默认异常提示信息
     */
    String DEFAULT_EXCEPTION_MSG = "系统异常，请稍后重试";

    /**
     * 顶级权限 父类ID 标志
     */
    Integer TOP_PARENT_ID = 0;

    /**
     * 开启/启用/是 标志位
     */
    Integer ENABLE = 1;

    /**
     * 关闭/禁用/否 标志位
     */
    Integer DISABLE = 0;

}
