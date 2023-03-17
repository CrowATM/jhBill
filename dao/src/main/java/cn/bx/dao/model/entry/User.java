package cn.bx.dao.model.entry;

/**
 * @Description 用户
 * @Author ZK
 * @Date 2023/3/17 16:46
 */

public class User {
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
