package cn.bx.core.util;

import org.springframework.cache.Cache;

import java.util.HashMap;
import java.util.Map;

/** ehcache工具
 * @Author zk
 * @CreateDateTime 2022/6/23 10:53
 */
public class CacheUtil {

    @SuppressWarnings("all")
    public static <T> Map<String, T> getCacheMap(Cache cache) {
        HashMap<String, T> stringTHashMap = new HashMap<>();
        try {
            assert cache != null;
            javax.cache.Cache nativeCache = (javax.cache.Cache) (cache.getNativeCache());
            for (Object o : nativeCache) {
                javax.cache.Cache.Entry next = (javax.cache.Cache.Entry) o;
                if (next != null) {
                    Object key = next.getKey();
                    Object value = next.getValue();
                    T v = value != null ? (T) value : null;
                    stringTHashMap.put((String) key, v);
                }
            }
        }catch (Exception ex){
            throw new RuntimeException("无法获取所有cache并转为map:" + ex.getMessage(), ex);
        }
        return stringTHashMap;
    }
}
