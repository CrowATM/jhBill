package cn.bx.core.config.security.handler;

import cn.bx.core.config.security.support.ResultCode;
import cn.bx.core.response.ResponseData;
import cn.bx.core.response.ResponseDataUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 权限拒绝处理逻辑
 */
@Component
public class CustomizeAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ResponseDataUtil.writeResponseByCodeEnum(httpServletResponse, new ResponseData<>(ResultCode.NO_PERMISSION));
    }
}
