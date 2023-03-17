package cn.bx.core.config.swagger.support;


import java.util.Date;

/**
 * @Description 登陆用户信息
 * @Author ZK
 * @Date 2023/3/16 19:15
 */
public class UserLoginInfo {
    private String username;
    private Date loginDate;
    private String loginIp;

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
