package cn.bx.core.util;

import cn.bx.core.config.ehcache.support.JwtTokenService;
import cn.bx.core.config.security.model.SecurityModel;
import cn.bx.core.config.security.model.UserLoginToken;
import cn.bx.core.config.swagger.support.UserLoginInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description toke验证
 * @Author ZK
 * @Date 2023/3/16 18:55
 */
public class TokenVerifyUtil {

    private final static Logger log = LoggerFactory.getLogger(TokenVerifyUtil.class);

    public static Authentication getAuthentication(HttpServletRequest request, UserDetailsService userDetailsService) {

        UserDetails userDetails;
        String username;
        SecurityModel securityModel = SpringBeanUtil.getBeanByClass(SecurityModel.class);
        JwtTokenService jwtTokenService = SpringBeanUtil.getBeanByClass(JwtTokenService.class);
        try {
            //存放在头部的token
            String tokenHeader = request.getHeader(securityModel.getToken().getHeaderName());
            if (StringUtils.isEmpty(tokenHeader)) {
                return null;
            }
            //验证并获取name
            username = JwtUtil.getUsername(tokenHeader);
            if (StringUtils.isEmpty(username)){
                return null;
            }
            //从缓存中获取登陆用户信息
            UserLoginToken userLoginToken = jwtTokenService.getUserLoginTokenByKey(tokenHeader);
            //判断是否存在
            if (userLoginToken == null) {
                return null;
            }
            //验证用户名密码
            userDetails = userDetailsService.loadUserByUsername(username);
            String password = userDetails.getPassword();
            if (!JwtUtil.verify(tokenHeader, username, password)) {
                return null;
            }
        } catch (Exception e) {
            log.warn("从请求头部获取" + securityModel.getToken().getHeaderName() + "并验证其中的token失败", e);
            return null;
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
