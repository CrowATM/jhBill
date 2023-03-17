package cn.bx.core.config.security.model;

/**
 * @Description token配置
 * @Author ZK
 * @Date 2023/3/16 19:24
 */
public class SecurityToken {
    /**
     * 请求头上的token名称
     */
    private String headerName;
    /**
     * 过期时间
     */
    private long expireTime = 23 * 60 * 60 * 1000;

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }


    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }
}
