package cn.bx.core.config.swagger.support;

import cn.bx.core.common.YmlSourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Description 项目信息
 * @Author ZK
 * @Date 2023/3/16 15:13
 */
@Component
@PropertySource(value = "classpath:swagger.yml", factory = YmlSourceFactory.class)
@ConfigurationProperties(prefix = "app-doc")
public class AppDoc {

    private String version;

    private String description;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
