package kr.co.F1FS.app.global.util;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.notification.domain.Notification;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.suggest.domain.Suggest;
import kr.co.F1FS.app.domain.user.domain.User;
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
            @CacheEvict(value = "Driver", key = "#driver.id", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "DriverByNumber", key = "#driver.number", cacheManager = "redisLongCacheManager")
    })
    public void evictCachingDriver(Driver driver){}

    @Caching(evict = {
            @CacheEvict(value = "ConstructorDTO", key = "#constructor.id", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "ConDriverList", key = "#constructor.id", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "Constructor", key = "#constructor.id", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "ConstructorByName", key = "#constructor.name", cacheManager = "redisLongCacheManager")
    })
    public void evictCachingConstructor(Constructor constructor){}

    @Caching(evict = @CacheEvict(value = "PostReplyList", key = "#reply.post.id", cacheManager = "redisLongCacheManager"))
    public void evictCachingReply(Reply reply){}

    @Caching(evict = {
            @CacheEvict(value = "UserDTO", key = "#user.nickname", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "User", key = "#user.nickname", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "Username", key = "#user.username", cacheManager = "redisLongCacheManager")
    })
    public void evictCachingUser(User user){}

    @Caching(evict = {
            @CacheEvict(value = "SuggestDTO", key = "#suggest.id", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "Suggest", key = "#suggest.id", cacheManager = "redisLongCacheManager")
    })
    public void evictCachingSuggest(Suggest suggest){}

    @Caching(evict = {
            @CacheEvict(value = "NotificationDTOByRedisId", key = "#notification.redisId", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "Notification", key = "#notification.id", cacheManager = "redisLongCacheManager")
    })
    public void evictCachingNotification(Notification notification){}
}
