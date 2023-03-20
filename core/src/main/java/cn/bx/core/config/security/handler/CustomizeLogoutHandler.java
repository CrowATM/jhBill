package cn.bx.core.config.security.handler;

import cn.bx.core.config.ehcache.support.JwtTokenService;
import cn.bx.core.config.security.model.SecurityModel;
import cn.bx.core.util.SpringBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 登出处理器
 * @Author zk
 * @CreateDateTime 2022/5/17 16:52
 */
@Component
public class CustomizeLogoutHandler implements LogoutHandler {

    private final JwtTokenService jwtTokenService;
    private final SecurityModel securityModel;

    @Autowired
    public CustomizeLogoutHandler(JwtTokenService jwtTokenService, SecurityModel securityModel) {
        this.jwtTokenService = jwtTokenService;
        this.securityModel = securityModel;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        //移除token缓存
        jwtTokenService.removeLoginCacheByToken(request.getHeader(securityModel.getToken().getHeaderName()));
    }
}
