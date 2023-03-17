package cn.bx.web.config.swagger;

import cn.bx.core.config.swagger.Swagger2ModelsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @Description swagger
 * @Author ZK
 * @Date 2023/3/16 16:01
 */
@Configuration
public class WebSwaggerConfig {

    private final Swagger2ModelsConfig swagger2ModelsConfig;

    public WebSwaggerConfig(Swagger2ModelsConfig swagger2ModelsConfig) {
        this.swagger2ModelsConfig = swagger2ModelsConfig;
    }

    @Bean
    public Docket webDocketApi() {
        return swagger2ModelsConfig.docket("web", "通用", "cn.bx.web");
    }

}
