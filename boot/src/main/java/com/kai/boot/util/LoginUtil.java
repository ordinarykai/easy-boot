package com.kai.boot.util;

import com.kai.boot.api.exception.UnauthorizedException;
import com.kai.boot.constant.RedisConstant;
import com.kai.boot.redis.service.RedisService;
import com.kai.boot.util.bo.LoginInfo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Objects;

import static com.kai.boot.constant.CommonConstant.EXPIRE_TIME;
import static com.kai.boot.constant.CommonConstant.TOKEN;

/**
 * @author kai
 * @date 2022/3/12 13:39
 */
@AllArgsConstructor
public class LoginUtil {

    private RedisService redisService;

    /**
     * 存储用户信息
     */
    public String set(LoginInfo loginInfo) {
        String token = StringUtil.getUuid();
        redisService.set(RedisConstant.REDIS_LOGIN + token, loginInfo, EXPIRE_TIME);
        return token;
    }

    /**
     * 获取用户信息
     *
     * @param needLogin 是否需要登录 true:是 false:否
     */
    public LoginInfo get(boolean needLogin) {
        LoginInfo loginInfo = get();
        if (needLogin && Objects.isNull(loginInfo)) {
            throw new UnauthorizedException();
        }
        return loginInfo;
    }

    /**
     * 获取用户信息
     */
    public LoginInfo get() {
        String token = ServletUtil.getRequest().getHeader(TOKEN);
        return get(token);
    }

    /**
     * 获取用户信息
     */
    public LoginInfo get(String token) {
        if (!StringUtils.hasText(token)) {
            return null;
        }
        LoginInfo loginInfo = redisService.get(RedisConstant.REDIS_LOGIN + token);
        if (Objects.nonNull(loginInfo)) {
            redisService.expire(RedisConstant.REDIS_LOGIN + token, EXPIRE_TIME);
        }
        return loginInfo;
    }

    /**
     * 踢出用户
     */
    public void out() {
        out(ServletUtil.getRequest().getHeader(TOKEN));
    }

    /**
     * 踢出指定用户
     *
     * @param token 登录token令牌
     */
    public void out(String token) {
        String key = RedisConstant.REDIS_LOGIN + token;
        if (StringUtils.hasText(token) && redisService.hasKey(key)) {
            redisService.del(key);
        }
    }

}
