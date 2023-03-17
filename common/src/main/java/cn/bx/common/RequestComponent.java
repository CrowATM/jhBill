package cn.bx.common;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * request 工具
 */
@SuppressWarnings("all")
@Component
public class RequestComponent {

    private final HttpServletRequest httpServletRequest;

    @Autowired
    public RequestComponent(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    /**
     * 获取真实ip地址，避免获取代理ip
     */
    public static String getIpAddress(HttpServletRequest httpServletRequest) {
        String ip = httpServletRequest.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = httpServletRequest.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = httpServletRequest.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = httpServletRequest.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = httpServletRequest.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = httpServletRequest.getHeader("HTTP_X_FORWARDED_FOR");
        }
        return ip;
    }

    /**
     * 获取真实ip地址，避免获取代理ip
     */
    public String getIpAddress() {
        return getIpAddress(httpServletRequest);
    }

    /**
     * 获取客户端真实ip地址，避免获取代理ip
     */
    @Deprecated
    public String getKhdIpAddress() {
        String ip = httpServletRequest.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = httpServletRequest.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = httpServletRequest.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = httpServletRequest.getRemoteAddr();
        }
        return ip;
    }


    public String getRequestMethod(){
        return httpServletRequest.getMethod();
    }

    public String getRequestUrI(){
        return httpServletRequest.getRequestURI();
    }

    public String getHeader(String headName){
        return httpServletRequest.getHeader(headName);
    }

    /**
     * 获取服务器ip
     * @return ServiceIpAddress or null
     */
    public static String getServiceIpAddress() {
        String hostAddress = null;
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            //do nothing
        }
        return hostAddress;
    }
}
