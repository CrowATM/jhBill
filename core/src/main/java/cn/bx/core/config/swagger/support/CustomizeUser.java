package cn.bx.core.config.swagger.support;

/**
 * @Description 登陆检查 用户信息
 * @Author ZK
 * @Date 2023/3/16 18:01
 */
public class CustomizeUser {

    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
