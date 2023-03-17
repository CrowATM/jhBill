package cn.bx.core.config.security.handler;


import cn.bx.common.RequestComponent;
import cn.bx.core.config.ehcache.support.JwtTokenService;
import cn.bx.core.config.security.model.SecurityModel;
import cn.bx.core.config.security.model.UserLoginToken;
import cn.bx.core.config.security.support.CustomizeUserDetail;
import cn.bx.core.config.security.support.ResultCode;
import cn.bx.core.config.security.support.UserDetailsServiceImpl;
import cn.bx.core.response.ResponseData;
import cn.bx.core.response.ResponseDataUtil;
import cn.bx.core.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * 验证成功处理器
 */
@Component
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final static Logger log = LoggerFactory.getLogger(CustomizeAuthenticationSuccessHandler.class);

    private final JwtTokenService jwtTokenService;
    private final RequestComponent requestComponent;
    private final UserDetailsServiceImpl userService;
    private final SecurityModel securityModel;

    @Autowired
    public CustomizeAuthenticationSuccessHandler(JwtTokenService jwtTokenService, RequestComponent requestComponent, UserDetailsServiceImpl userService, SecurityModel securityModel) {
        this.jwtTokenService = jwtTokenService;
        this.requestComponent = requestComponent;
        this.userService = userService;
        this.securityModel = securityModel;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        CustomizeUserDetail user = (CustomizeUserDetail) authentication.getPrincipal();
        String password = user.getPassword();
        String username = user.getUsername();
        String token;
        try {
            //生成token
            token = Optional.ofNullable(JwtUtil.sign(username, password)).orElseThrow(() -> new RuntimeException("生成token签名失败!"));
            //设置token缓存
            jwtTokenService.putLoginInCache(token, new UserLoginToken(user, requestComponent.getIpAddress()));
            //更新数据库user表登陆时间
            userService.updateUser(username);
        }catch (Exception ex) {
            log.error("验证成功,登陆处理失败！", ex);
            ResponseDataUtil.writeResponseByCodeEnum(httpServletResponse, new ResponseData<>(ResultCode.COMMON_FAIL, "验证成功,登陆处理失败!"));
            return;
        }
        //设置响应,写入token
        httpServletResponse.setHeader(securityModel.getToken().getHeaderName(), token);
        ResponseDataUtil.writeResponseByCodeEnum(httpServletResponse, new ResponseData<>(ResultCode.SUCCESS));
    }
}
