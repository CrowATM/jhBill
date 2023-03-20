package cn.bx.core.config.security.filter;

import cn.bx.core.util.TokenVerifyUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 表单认证用户名密码过滤器
 */
public class CustomizeUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public CustomizeUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (TokenVerifyUtil.verifyHeaderPostAndJson(request)) {
            Map<String, String> map;
            UsernamePasswordAuthenticationToken authRequest = null;
            try {
                //转换输入的json数据为map格式
                map = new ObjectMapper().readValue(request.getInputStream(), new TypeReference<Map<String, String>>() {});
                //获取username的值
                String username = map.get(getUsernameParameter());//getUsernameParameter：username
                //获取password
                String password = map.get(getPasswordParameter());//getPasswordParameter：password
                //设置用户名密码
                authRequest = new UsernamePasswordAuthenticationToken(username, password);
            } catch (IOException e) {
                authRequest = new UsernamePasswordAuthenticationToken("", "");
            } finally {
                setDetails(request, authRequest);
            }
            return this.getAuthenticationManager().authenticate(authRequest);
        }
        return super.attemptAuthentication(request, response);
    }


}
