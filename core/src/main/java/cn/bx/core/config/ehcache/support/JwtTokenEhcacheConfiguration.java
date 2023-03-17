package cn.bx.core.config.ehcache.support;

import cn.bx.common.YmlSourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Description EhCache配置项
 * @Author ZK
 * @Date 2023/3/17 14:48
 */
@Deprecated
//@Component
//@PropertySource(value = "classpath:ehcache.yml", factory = YmlSourceFactory.class)
//@ConfigurationProperties("cache.ehcache")
public class JwtTokenEhcacheConfiguration {
    /**
     * 缓存key数量
     */
    private int heap = 1000;
    /**
     * 内存大小,MB
     */
    private int offHeap = 100;
    /**
     * 持久化地址
     */
    private String diskDir = "/app/cache";
    /**
     * 持久化大小,MB
     * diskDir存在才生效
     */
    private int disk = 100;

    /**
     * 过期时间,分钟
     */
    private int expiryTime = 60;

    public int getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(int expiryTime) {
        this.expiryTime = expiryTime;
    }

    public int getHeap() {
        return heap;
    }

    public void setHeap(int heap) {
        this.heap = heap;
    }

    public int getOffHeap() {
        return offHeap;
    }

    public void setOffHeap(int offHeap) {
        this.offHeap = offHeap;
    }

    public String getDiskDir() {
        return diskDir;
    }

    public void setDiskDir(String diskDir) {
        this.diskDir = diskDir;
    }

    public int getDisk() {
        return disk;
    }

    public void setDisk(int disk) {
        this.disk = disk;
    }
}
