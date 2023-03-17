package cn.bx.core.config.ehcache;

import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.net.URL;

/**
 * @Description ehcache配置
 * @Author ZK
 * @Date 2023/3/17 15:47
 */
//@Configuration
//@EnableCaching
@Deprecated
public class CustomizeEhcacheConfig {

    private final static Logger log = LoggerFactory.getLogger(CustomizeEhcacheConfig.class);

    @Bean
    public CacheManager getCacheManager() throws IOException {
        try {
            URL url = new ClassPathResource("jcache.xml").getURL();
            CacheManager cacheManager = CacheManagerBuilder.newCacheManager(new XmlConfiguration(url));
            cacheManager.init();
            return cacheManager;
        } catch (IOException ex) {
            log.error("加载ehcache.xml");
            throw ex;
        }
    }
}
