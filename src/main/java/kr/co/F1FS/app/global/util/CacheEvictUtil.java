package kr.co.F1FS.app.global.util;

import kr.co.F1FS.app.domain.circuit.domain.Circuit;
import kr.co.F1FS.app.domain.complain.note.domain.NoteComplain;
import kr.co.F1FS.app.domain.complain.post.domain.PostComplain;
import kr.co.F1FS.app.domain.complain.reply.domain.ReplyComplain;
import kr.co.F1FS.app.domain.complain.user.domain.UserComplain;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import kr.co.F1FS.app.domain.note.domain.Note;
import kr.co.F1FS.app.domain.notification.domain.Notification;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.session.domain.Session;
import kr.co.F1FS.app.domain.suggest.domain.Suggest;
import kr.co.F1FS.app.domain.user.domain.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

@Component
public class CacheEvictUtil {
    @Caching(evict = {
            @CacheEvict(value = "PostDTO", key = "#post.id", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "PostDTOForAdmin", key = "#post.id", cacheManager = "redisLongCacheManager")
    })
    public void evictCachingPost(Post post){}

    @Caching(evict = {
            @CacheEvict(value = "DriverDTO", key = "#driver.id", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "Driver", key = "#driver.id", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "DriverByNumber", key = "#driver.number", cacheManager = "redisLongCacheManager")
    })
    public void evictCachingDriver(Driver driver){}

    @Caching(evict = {
            @CacheEvict(value = "ConstructorDTO", key = "#constructor.id", cacheManager = "redisLongCacheManager")
    })
    public void evictCachingConstructor(Constructor constructor){}

    @Caching(evict = @CacheEvict(value = "PostReplyList", key = "#post.id", cacheManager = "redisLongCacheManager"))
    public void evictCachingReply(Post post){}

    @Caching(evict = {
            @CacheEvict(value = "UserDTO", key = "#user.nickname", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "FollowingUser", key = "#user.id", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "FollowerUser", key = "#user.id", cacheManager = "redisLongCacheManager")
    })
    public void evictCachingUser(User user){}

    @Caching(evict = {
            @CacheEvict(value = "SuggestDTO", key = "#suggest.id", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "SuggestDTOForAdmin", key = "#suggest.id", cacheManager = "redisLongCacheManager")
    })
    public void evictCachingSuggest(Suggest suggest){}

    @Caching(evict =
            @CacheEvict(value = "NotificationDTOByRedisId", key = "#notification.redisId", cacheManager = "redisLongCacheManager")
    )
    public void evictCachingNotification(Notification notification){}

    @Caching(evict = @CacheEvict(value = "NoteDTO", key = "#note.id", cacheManager = "redisLongCacheManager"))
    public void evictCachingNote(Note note){}

    @Caching(evict = {
            @CacheEvict(value = "CircuitDTO", key = "#circuit.id", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "SimpleCircuitDTO", key = "#circuit.id", cacheManager = "redisLongCacheManager")
    })
    public void evictCachingCircuit(Circuit circuit){}

    @Caching(evict = {
            @CacheEvict(value = "GrandPrixDTO", key = "#grandPrix.id", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "GrandPrixDTOForAdmin", key = "#grandPrix.id", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "GrandPrixList", key = "#grandPrix.season", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "GrandPrixListForAdmin", key = "#grandPrix.season", cacheManager = "redisLongCacheManager")
    })
    public void evictCachingGrandPrix(GrandPrix grandPrix){}

    @Caching(evict = @CacheEvict(value = "SessionDTO", key = "#session.id", cacheManager = "redisLongCacheManager"))
    public void evictCachingSession(Session session){}

    @Caching(evict = {
            @CacheEvict(value = "ConstructorStandingList", key = "#racingClassCode", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "DriverStandingList", key = "#racingClassCode", cacheManager = "redisLongCacheManager")
    })
    public void evictCachingStandingList(String racingClassCode){}

    @Caching(evict = {
            @CacheEvict(value = "UserComplainDTO", key = "#userComplain.id", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "UserComplainDTOForAdmin", key = "#userComplain.id", cacheManager = "redisLongCacheManager")
    })
    public void evictCachingUserComplain(UserComplain userComplain){}

    @Caching(evict = {
            @CacheEvict(value = "PostComplainDTO", key = "#postComplain.id", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "PostComplainDTOForAdmin", key = "#postComplain.id", cacheManager = "redisLongCacheManager")
    })
    public void evictCachingPostComplain(PostComplain postComplain){}

    @Caching(evict = {
            @CacheEvict(value = "ReplyComplainDTO", key = "#replyComplain.id", cacheManager = "redisLongCacheManager"),
            @CacheEvict(value = "ReplyComplainDTOForAdmin", key = "#replyComplain.id", cacheManager = "redisLongCacheManager")
    })
    public void evictCachingReplyComplain(ReplyComplain replyComplain){}

    @Caching(evict = {
            @CacheEvict(value = "NoteComplainDTO", key = "#noteComplain.id", cacheManager = "redisLongCacheManager")
    })
    public void evictCachingNoteComplain(NoteComplain noteComplain){}
}
