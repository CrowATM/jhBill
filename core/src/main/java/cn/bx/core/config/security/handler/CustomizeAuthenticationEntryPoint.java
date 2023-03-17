package cn.bx.core.config.security.handler;

import cn.bx.core.config.security.support.ResultCode;
import cn.bx.core.response.ResponseData;
import cn.bx.core.response.ResponseDataUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 匿名用户访问无权限资源时的异常
 */
@Component
public class CustomizeAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        ResponseDataUtil.writeResponseByCodeEnum(httpServletResponse, new ResponseData<>(ResultCode.USER_NOT_LOGIN));
    }
}
