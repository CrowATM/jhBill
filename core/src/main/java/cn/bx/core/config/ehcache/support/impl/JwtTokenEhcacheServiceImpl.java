package cn.bx.core.config.ehcache.support.impl;

import cn.bx.core.config.ehcache.support.JwtTokenService;
import cn.bx.core.config.security.model.SecurityModel;
import cn.bx.core.config.security.model.UserLoginToken;
import cn.bx.core.util.CacheUtil;
import cn.bx.core.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @Description
 * @Author ZK
 * @Date 2023/3/17 17:45
 */
@Service
public class JwtTokenEhcacheServiceImpl implements JwtTokenService {
    private final static Logger log = LoggerFactory.getLogger(JwtTokenEhcacheServiceImpl.class);

    private final CacheManager cacheManager;
    private final SecurityModel securityModel;

    @Autowired
    public JwtTokenEhcacheServiceImpl(CacheManager cacheManager, SecurityModel securityModel) {
        this.cacheManager = cacheManager;
        this.securityModel = securityModel;
    }


    @Override
    public UserLoginToken getUserLoginTokenByKey(String key) {
        Cache cache = getJwtTokenCache();
        return Objects.requireNonNull(cache).get(key, UserLoginToken.class);
    }

    private Cache getJwtTokenCache() {
        return cacheManager.getCache(securityModel.getJwtCacheName());
    }

    @Override
    public void putLoginInCache(String key, UserLoginToken val) {
        Cache cache = getJwtTokenCache();
        Objects.requireNonNull(cache).put(key, val);
    }

    @Override
    public void removeCacheByUsername(String username) {
        Cache cache = getJwtTokenCache();
        if(cache != null) {
            try {
                Map<String, Object> cacheMap = CacheUtil.getCacheMap(cache);
                cacheMap.keySet().stream().filter(Objects::nonNull).forEach(a -> {
                    String username1 = JwtUtil.getUsername(a);
                    if (StringUtils.equals(username1, username)) {
                        cache.evictIfPresent(a);
                    }
                });
            }catch (Exception ex){
                log.warn("移除用户" + username + "的登录信息缓存时失败:" + ex.getMessage(), ex);
            }
        }
    }

    @Override
    public void removeLoginCacheByToken(String key) {
        Cache cache = getJwtTokenCache();
        if (cache != null){
            cache.evictIfPresent(key);
        }
    }
}
