package kr.co.F1FS.app.global.util;

import kr.co.F1FS.app.domain.model.rdb.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

@Component
public class CacheEvictUtil {
    @Caching(evict = {
            @CacheEvict(value = "PostNotDTO", key = "#post.id", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "PostDTO", key = "#post.id", cacheManager = "redisLongCacheManager")
    })
    public void evictCachingPost(Post post){}

    @Caching(evict = {
            @CacheEvict(value = "DriverDTO", key = "#driver.id", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "Driver", key = "#driver.id", cacheManager = "redisLongCacheManager")
    })
    public void evictCachingDriver(Driver driver){}

    @Caching(evict = {
            @CacheEvict(value = "ConstructorDTO", key = "#constructor.id", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "ConDriverList", key = "#constructor.id", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "Constructor", key = "#constructor.id", cacheManager = "redisLongCacheManager")
    })
    public void evictCachingConstructor(Constructor constructor){}

    @Caching(evict = @CacheEvict(value = "ReplyList", key = "#reply.post.id", cacheManager = "redisLongCacheManager"))
    public void evictCachingReply(Reply reply){}

    @Caching(evict = {
            @CacheEvict(value = "UserDTO", key = "#user.nickname", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "User", key = "#user.nickname", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "Username", key = "#user.username", cacheManager = "redisLongCacheManager")
    })
    public void evictCachingUser(User user){}
}
