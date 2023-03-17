package cn.bx.core.config.swagger.support;

import cn.bx.core.common.YmlSourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import springfox.documentation.service.Contact;

/**
 * @Description 开发者信息
 * @Author ZK
 * @Date 2023/3/16 14:55
 */
@Component
@PropertySource(value = "classpath:swagger.yml", factory = YmlSourceFactory.class)
@ConfigurationProperties(prefix = "contact")
public class CustomizeContact {

    private String mobile;
    private String name;
    private String url;
    private String email;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Contact getContact() {
        return new Contact(name, url, email);
    }
}
