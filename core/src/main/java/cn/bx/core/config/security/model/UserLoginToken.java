package cn.bx.core.config.security.model;


import cn.bx.core.config.security.support.CustomizeUserDetail;

import java.util.Date;

/**
 * @Description token value中的用户信息
 * @Author ZK
 * @Date 2023/3/17 11:44
 */
public class UserLoginToken {
    private String username;
    private Date loginDate;
    private String loginIp;

    public UserLoginToken(CustomizeUserDetail user, String ipAddress) {
        this.username = user.getUsername();
        this.loginDate = new Date();
        this.loginIp = ipAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }
}
