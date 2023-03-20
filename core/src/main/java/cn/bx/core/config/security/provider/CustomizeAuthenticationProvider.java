package cn.bx.core.config.security.provider;

import cn.bx.core.config.security.support.CustomizeUserDetail;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/** 登陆鉴权,login请求验证用户
 * @Author zk
 * @Date 2022/5/17 11:01
 */
public class CustomizeAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public CustomizeAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 鉴权具体逻辑
     * UsernamePasswordAuthenticationToken
     * */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CustomizeUserDetail userDetails = (CustomizeUserDetail)userDetailsService.loadUserByUsername(authentication.getName());
//        if (!StringUtils.equals("0", userDetails.getDelFlag())){
//            //用户已删除
//            throw new DisabledException("User is disabled");
//        }
//        if (!StringUtils.equals("0", userDetails.getStatus())){
//            //用户被锁定
//            throw new LockedException("User account is locked");
//        }
        //验证密码
        String password = authentication.getCredentials().toString();
        //密码passwordEncoder加密
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Bad credentials");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials());
    }

    /**
     * 是否支持当前鉴权逻辑
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

}
