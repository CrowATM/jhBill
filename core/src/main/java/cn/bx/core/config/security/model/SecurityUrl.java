package cn.bx.core.config.security.model;

import java.util.List;

/**
 * @Description 路径配置
 * @Author ZK
 * @Date 2023/3/16 19:22
 */
public class SecurityUrl {
    /**
     * 登陆路径
     */
    private String login;
    /**
     * 登出路径
     */
    private String logout;
    /**
     * 允许重复读取request流的路径
     */
    private List<String> allowRepeatUrls;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogout() {
        return logout;
    }

    public void setLogout(String logout) {
        this.logout = logout;
    }

    public List<String> getAllowRepeatUrls() {
        return allowRepeatUrls;
    }

    public void setAllowRepeatUrls(List<String> allowRepeatUrls) {
        this.allowRepeatUrls = allowRepeatUrls;
    }
}
