package cn.bx.core.config.security.filter;

import cn.bx.core.config.security.model.SecurityModel;
import cn.bx.core.util.SpringBeanUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/** 允许指定请求路径重复读取request流
 * @Author zk
 * @CreateDateTime 2022/8/11 10:11
 */
public class RequestInputStreamFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //配置的允许重复读取request流的路径
        List<String> allowRepeatUrls = SpringBeanUtil.getBeanByClass(SecurityModel.class).getUrl().getAllowRepeatUrls();
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (CollectionUtils.isNotEmpty(allowRepeatUrls) && allowRepeatUrls.stream().anyMatch(a -> StringUtils.endsWith(httpServletRequest.getRequestURI(), a))){
            chain.doFilter(new CustomizeHttpServletRequestWrapper(httpServletRequest), response);
        }else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
