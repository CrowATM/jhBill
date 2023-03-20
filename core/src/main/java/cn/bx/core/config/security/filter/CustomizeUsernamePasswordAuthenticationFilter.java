package cn.bx.core.config.security.filter;

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
        if (verifyHeader(request)) {
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

    //form => application/x-www-form-urlencoded
    //注意大小写
    @SuppressWarnings("deprecation")
    private boolean verifyHeader(HttpServletRequest request) {
        //判断请求方法是否是post
        if (StringUtils.equalsIgnoreCase("POST", request.getMethod())) {
            throw new AuthenticationServiceException("不支持当前请求方式: " + request.getMethod());
        } else {
            //判断请求头的content是否是json格式
            String contentType = request.getContentType().toLowerCase();
            return StringUtils.equalsAnyIgnoreCase(contentType, MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_JSON_VALUE);
        }
    }
}
