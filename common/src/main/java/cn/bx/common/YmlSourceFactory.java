package cn.bx.common;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/** 加载yml配置文件
 * @Author zk
 * @CreateDateTime 2022/8/9 18:46
 */
public class YmlSourceFactory implements PropertySourceFactory {
    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource encodedResource) throws IOException {
        Properties propertiesFromYaml = loadYamlIntoProperties(encodedResource);
        String sourceName = name != null ? name : encodedResource.getResource().getFilename();
        assert sourceName != null;
        return new PropertiesPropertySource(sourceName, propertiesFromYaml);
    }

    private Properties loadYamlIntoProperties(EncodedResource resource) throws FileNotFoundException {
        try {
            YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
            factory.setResources(resource.getResource());
            factory.afterPropertiesSet();
            return factory.getObject();
        } catch (IllegalStateException e) {
            // for ignoreResourceNotFound
            Throwable cause = e.getCause();
            if (cause instanceof FileNotFoundException) {
                throw (FileNotFoundException) e.getCause();
            }
            throw e;
        }
    }
}
