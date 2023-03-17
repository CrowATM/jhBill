package cn.bx.core.config.ehcache.support;

/**
 * @Description ehCache配置
 * @Author ZK
 * @Date 2023/3/17 14:45
 */
//@EnableCaching
//@Configuration
//@Order(100)
    @Deprecated
public class _JwtTokenEhcacheConfig {

//    private final JwtTokenEhcacheConfiguration jwtTokenEhcacheConfiguration;
//
//    @Autowired
//    public JwtTokenEhcacheConfig(JwtTokenEhcacheConfiguration jwtTokenEhcacheConfiguration) {
//        this.jwtTokenEhcacheConfiguration = jwtTokenEhcacheConfiguration;
//    }
//
//    public CacheManager getCacheManager() {
////        ResourcePoolsBuilder resourcePoolsBuilder = ResourcePoolsBuilder.newResourcePoolsBuilder()
////                .heap(jwtTokenEhcacheConfiguration.getHeap(), EntryUnit.ENTRIES)
////                .offheap(jwtTokenEhcacheConfiguration.getOffHeap(), MemoryUnit.MB)
////                .disk(jwtTokenEhcacheConfiguration.getDisk(), MemoryUnit.MB);
////        ExpiryPolicy<Object, Object> expiryPolicy = jwtTokenEhcacheConfiguration.getExpiryTime() > 0 ? ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofMillis(jwtTokenEhcacheConfiguration.getExpiryTime())) : ExpiryPolicyBuilder.noExpiration();
////        CacheConfigurationBuilder.newCacheConfigurationBuilder()
//    }
}
