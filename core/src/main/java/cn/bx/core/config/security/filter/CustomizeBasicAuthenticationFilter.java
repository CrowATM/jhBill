package cn.bx.core.config.security.filter;

import cn.bx.core.config.security.support.ResultCode;
import cn.bx.core.response.ResponseData;
import cn.bx.core.response.ResponseDataUtil;
import cn.bx.core.util.TokenVerifyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/** token的认证过滤器
 * @Author zk
 * @Date 2022/5/16 14:50
 */
public class CustomizeBasicAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    public CustomizeBasicAuthenticationFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        // 判断是否有 token，并且进行认证
        Authentication token = TokenVerifyUtil.getAuthentication(request, userDetailsService);
        if (token != null) {
            // 认证成功
            SecurityContextHolder.getContext().setAuthentication(token);
            chain.doFilter(request, response);
        } else {
            ResponseDataUtil.writeResponseByCodeEnum(response, new ResponseData<>(ResultCode.USER_NOT_LOGIN));
        }
    }

}
