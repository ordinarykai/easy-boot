package com.easy.boot.core.auth;

import cn.hutool.extra.spring.SpringUtil;
import com.easy.boot.core.api.exception.UnauthorizedException;
import com.easy.boot.core.redis.service.RedisService;
import com.easy.boot.core.util.ServletUtil;
import com.easy.boot.core.util.StringUtil;
import com.easy.boot.core.util.bo.AuthInfo;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * 认证工具类
 *
 * @author kai
 * @date 2022/3/12 13:39
 */
public class AuthUtil {

    private static final RedisService redisService;
    private static final AuthProperties authProperties;

    static {
        redisService = SpringUtil.getBean(RedisService.class);
        authProperties = SpringUtil.getBean(AuthProperties.class);
    }

    /**
     * 存储用户信息
     */
    public static String set(AuthInfo authInfo) {
        String token = StringUtil.getUuid();
        redisService.set(authProperties.getTokenKeyPrefix() + token, authInfo, authProperties.getExpireTime());
        return token;
    }

    /**
     * 更新当前用户信息（全量更新）
     */
    public static void update(AuthInfo authInfo) {
        String token = ServletUtil.getRequest().getHeader(authProperties.getTokenName());
        if (redisService.hasKey(authProperties.getTokenKeyPrefix() + token)) {
            redisService.set(authProperties.getTokenKeyPrefix() + token, authInfo, authProperties.getExpireTime());
        }
        update(authInfo, token);
    }

    /**
     * 更新指定用户信息（全量更新）
     */
    public static void update(AuthInfo authInfo, String token) {
        if (redisService.hasKey(authProperties.getTokenKeyPrefix() + token)) {
            redisService.set(authProperties.getTokenKeyPrefix() + token, authInfo, authProperties.getExpireTime());
        }
    }

    /**
     * 获取用户信息
     *
     * @param needLogin 是否需要登录 true:是 false:否
     */
    public static AuthInfo get(boolean needLogin) {
        AuthInfo authInfo = get();
        if (needLogin && Objects.isNull(authInfo)) {
            throw new UnauthorizedException();
        }
        return authInfo;
    }

    /**
     * 获取用户信息
     */
    public static AuthInfo get() {
        String token = ServletUtil.getRequest().getHeader(authProperties.getTokenName());
        return get(token);
    }

    /**
     * 获取用户信息
     */
    public static AuthInfo get(String token) {
        if (!StringUtils.hasText(token)) {
            return null;
        }
        AuthInfo authInfo = redisService.get(authProperties.getTokenKeyPrefix() + token);
        if (Objects.nonNull(authInfo)) {
            redisService.expire(authProperties.getTokenKeyPrefix() + token, authProperties.getExpireTime());
        }
        return authInfo;
    }

    /**
     * 踢出用户
     */
    public static void out() {
        out(ServletUtil.getRequest().getHeader(authProperties.getTokenName()));
    }

    /**
     * 踢出指定用户
     *
     * @param token 登录token令牌
     */
    public static void out(String token) {
        String key = authProperties.getTokenKeyPrefix() + token;
        if (StringUtils.hasText(token) && redisService.hasKey(key)) {
            redisService.del(key);
        }
    }

}
