package cn.bx.core.config.security.handler;

import cn.bx.core.config.security.support.ResultCode;
import cn.bx.core.response.ResponseData;
import cn.bx.core.response.ResponseDataUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败处理逻辑
 */
@Component
public class CustomizeAuthenticationFailureHandler implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {

        ResultCode resultCode;
        if (e instanceof AccountExpiredException) {
            //账号过期
            resultCode = ResultCode.USER_ACCOUNT_EXPIRED;
        } /*else if(e instanceof CanNotUseException){
            //账号未启用,密码为空
            //状态异常需继承AccountStatusException
//            result = ResultCode.CAN_NOT_USE;
        }*/ else if (e instanceof BadCredentialsException) {
            //密码错误
            resultCode = ResultCode.USER_CREDENTIALS_ERROR;
        } else if (e instanceof CredentialsExpiredException) {
            //密码过期
            resultCode = ResultCode.USER_CREDENTIALS_EXPIRED;
        } else if (e instanceof DisabledException) {
            //账号不可用
            resultCode = ResultCode.USER_ACCOUNT_DISABLE;
        } else if (e instanceof LockedException) {
            //账号锁定
            resultCode = ResultCode.USER_ACCOUNT_LOCKED;
        } else if (e instanceof InternalAuthenticationServiceException) {
            //用户不存在
            resultCode = ResultCode.USER_ACCOUNT_NOT_EXIST;
        } else {
            //其他错误
            resultCode = ResultCode.COMMON_FAIL;
        }

        ResponseDataUtil.writeResponseByCodeEnum(httpServletResponse, new ResponseData<>(resultCode));
    }
}
