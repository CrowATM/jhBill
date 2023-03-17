package cn.bx.core.config.ehcache.support;

import cn.bx.core.config.security.model.UserLoginToken;

/**
 * @Description token 缓存
 * @Author ZK
 * @Date 2023/3/17 17:22
 */
public interface JwtTokenService {

    /**
     * 通过前端的token获取存储在缓存中的用户信息
     * @return UserLoginToken
     */
    UserLoginToken getUserLoginTokenByKey(String key);

    /**
     * 添加用户登陆登陆信息到缓存
     */
    void putLoginInCache(String key, UserLoginToken val);

    /**
     * 移除指定用户登录token
     */
    void removeCacheByUsername(String username);

    /**
     * 移除指定登陆标识
     */
    void removeLoginCacheByToken(String key);
}
