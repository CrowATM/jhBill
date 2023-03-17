package cn.bx.core.config.security.manager;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * 访问决策管理器
 */
@Component
public class CustomizeAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        //此处可设置动态放行
        //当前用户所具有的角色
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        //如果没有角色，没有任何权限
        if (!CollectionUtils.isEmpty(authorities)) {
            for (ConfigAttribute ca : collection) {
                //当前请求需要的角色
                String needRole = ca.getAttribute();
                for (GrantedAuthority authority : authorities) {
                    if (authority.getAuthority().equals(needRole)) {
                        return;
                    }
                }
            }
        }
        throw new AccessDeniedException("权限不足!");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
