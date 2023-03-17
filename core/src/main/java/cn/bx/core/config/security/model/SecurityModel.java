package cn.bx.core.config.security.model;

import cn.bx.common.YmlSourceFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description security配置项
 * @Author ZK
 * @Date 2023/3/16 17:16
 */
@Component
@PropertySource(value = "classpath:security.yml", factory = YmlSourceFactory.class)
public class SecurityModel {

    private SecurityUrl url;

    private SecurityToken token;

    /**
     * 不走 Spring Security 过滤器链
     */
    private String[] securityPermitAll;

    /**
     * jwt ehcache的缓存名称
     */
    private String jwtCacheName = "jwt-cache";

    public String getJwtCacheName() {
        return jwtCacheName;
    }

    public void setJwtCacheName(String jwtCacheName) {
        this.jwtCacheName = jwtCacheName;
    }

    public SecurityUrl getUrl() {
        return url;
    }

    public void setUrl(SecurityUrl url) {
        this.url = url;
    }

    public SecurityToken getToken() {
        return token;
    }

    public void setToken(SecurityToken token) {
        this.token = token;
    }

    public String[] getSecurityPermitAll() {
        return securityPermitAll;
    }

    public void setSecurityPermitAll(String[] securityPermitAll) {
        this.securityPermitAll = securityPermitAll;
    }
}
