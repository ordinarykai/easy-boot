package com.easy.boot.core.auth;

import java.lang.annotation.*;

/**
 * 自定义接口权限注解
 *
 * @author kai
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface PreAuthorize {

    /**
     * 接口权限值
     */
    String value();

}
