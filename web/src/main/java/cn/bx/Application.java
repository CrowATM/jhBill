package cn.bx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

/**
 * @Description app入口
 * @Author ZK
 * @Date 2023/3/16 11:49
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class}, scanBasePackages = "cn.bx")
@MapperScan(basePackages = {"cn.bx.*.mapper"}, nameGenerator = Application.SpringBeanNameGenerator.class)
public class Application extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    //装载bean策略
    public static class SpringBeanNameGenerator extends AnnotationBeanNameGenerator {
        @Override
        protected String buildDefaultBeanName(BeanDefinition definition) {
            return definition.getBeanClassName();
        }
    }

    //使用外部tomcat容器时必须重写configure
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }
}
