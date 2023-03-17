package cn.bx.core.config.security.support;

/**
 * @Description 返回码定义,在此设置前后端约定
 * @Author ZK
 * @Date 2023/3/16 18:31
 */
public enum ResultCode {
    /* 成功 */
    SUCCESS(200, "成功"),
    /* 默认失败 */
    COMMON_FAIL(500, "失败"),

    USER_NOT_LOGIN(2001, "用户未登录"),
    USER_ACCOUNT_EXPIRED(2002, "账号已过期"),
    USER_CREDENTIALS_ERROR(2003, "密码错误"),
    USER_CREDENTIALS_EXPIRED(2004, "密码过期"),
    USER_ACCOUNT_DISABLE(2005, "账号不可用"),
    USER_ACCOUNT_LOCKED(2006, "账号被锁定"),
    USER_ACCOUNT_NOT_EXIST(2007, "账号不存在"),
    USER_ACCOUNT_ALREADY_EXIST(2008, "账号已存在"),
    USER_ACCOUNT_USE_BY_OTHERS(2009, "账号下线"),
    CAN_NOT_USE(2010, "账户未启用(密码为空)"),
    NO_PERMISSION(2011, "没有权限"),
    ;

    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    private void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
