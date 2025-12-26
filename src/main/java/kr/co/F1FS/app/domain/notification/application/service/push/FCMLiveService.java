package kr.co.F1FS.app.domain.notification.application.service.push;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import kr.co.F1FS.app.domain.notification.application.mapper.notice.NotificationMapper;
import kr.co.F1FS.app.domain.notification.application.port.in.redis.SaveNotificationRedisUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.notice.CreateNotificationUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.push.FCMLiveUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.redis.SubscribeNotificationRedisUseCase;
import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.reply.domain.ReplyComment;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.notification.domain.NotificationRedis;
import kr.co.F1FS.app.global.presentation.dto.notification.FCMPushDTO;
import kr.co.F1FS.app.global.util.AuthorCertification;
import kr.co.F1FS.app.global.util.FCMUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FCMLiveService implements FCMLiveUseCase {
    private final SaveNotificationRedisUseCase saveNotificationRedisUseCase;
    private final SubscribeNotificationRedisUseCase subscribeNotificationRedisUseCase;
    private final CreateNotificationUseCase createNotificationUseCase;
    private final NotificationMapper notificationMapper;
    private final FCMUtil fcmUtil;

    @Override
    public NotificationRedis sendPushForLiveInfo(FCMPushDTO dto){
        Notification notification = Notification.builder()
                .setTitle(dto.getTitle())
                .setBody(dto.getContent())
                .build();

        Message message = Message.builder()
                .setTopic(dto.getTopic())
                .setNotification(notification)
                .build();

        NotificationRedis redis = notificationMapper.toNotificationRedis(dto, "official");

        try{
            String response = String.valueOf(FirebaseMessaging.getInstance().sendAsync(message));
            createNotificationUseCase.save(redis, dto.getContent());
            saveNotificationRedisUseCase.saveNotification(redis, "topic:"+dto.getTopic());
            log.info("토픽 푸시 알림 전송 성공 : {}", response);
        } catch (Exception e){
            log.error("토픽 푸시 알림 전송 실패");
        }

        return redis;
    }

    @Override
    public void sendPushForAuthor(FCMPushDTO dto, FCMToken token, User user, Long contentId){
        NotificationRedis redis = notificationMapper.toNotificationRedis(dto, "personal");

        redis.setContentId(contentId);

        if(token != null){
            Notification notification = Notification.builder()
                    .setTitle(dto.getTitle())
                    .setBody(dto.getContent())
                    .build();

            Message message = Message.builder()
                    .setToken(token.getToken())
                    .setNotification(notification)
                    .build();

            try{
                String response = String.valueOf(FirebaseMessaging.getInstance().sendAsync(message));
                saveNotificationRedisUseCase.saveNotificationForPersonal(redis, user);
                log.info("토픽 푸시 알림 전송 성공 : {}", response);
            } catch (Exception e){
                log.error("토픽 푸시 알림 전송 실패");
            }
        }else {
            saveNotificationRedisUseCase.saveNotificationForPersonal(redis, user);
        }
    }

    @Override
    public void sendPushForFollow(FCMPushDTO dto, List<FCMToken> tokens, Long contentId){
        Notification notification = Notification.builder()
                .setTitle(dto.getTitle())
                .setBody(dto.getContent())
                .build();

        NotificationRedis redis = notificationMapper.toNotificationRedis(dto, "personal");

        redis.setContentId(contentId);

        for (FCMToken token : tokens){
            Message message = Message.builder()
                    .setToken(token.getToken())
                    .setNotification(notification)
                    .build();

            try{
                String response = String.valueOf(FirebaseMessaging.getInstance().sendAsync(message));
                saveNotificationRedisUseCase.saveNotificationForPersonal(redis, token.getUserId());
                log.info("토픽 푸시 알림 전송 성공 : {}", response);
            } catch (Exception e){
                log.error("토픽 푸시 알림 전송 실패");
            }
        }
    }

    @Override
    public void sendPushAfterPosting(Post post, User author) {
        List<FCMToken> tokens = fcmUtil.getFollowerToken(author).stream()
                .filter(token -> subscribeNotificationRedisUseCase.isSubscribe(token.getUserId(), "post"))
                .toList();
        if(!tokens.isEmpty()){
            FCMPushDTO pushDTO = fcmUtil.sendPushForPost(author, post.getTitle());
            sendPushForFollow(pushDTO, tokens, post.getId());
            log.info("팔로워 푸시 알림 전송 완료");
        }
    }

    @Override
    public void sendPushAfterPostLike(User user, Post post, Long id) {
        if(!AuthorCertification.certification(user.getUsername(), post.getAuthor().getUsername())){
            if(subscribeNotificationRedisUseCase.isSubscribe(post.getAuthor().getId(), "like")){
                FCMPushDTO pushDTO = fcmUtil.sendPushForLike(user);
                FCMToken token = fcmUtil.getAuthorToken(post.getAuthor());
                sendPushForAuthor(pushDTO, token, post.getAuthor(), id);
            }
        }
    }

    @Override
    public void sendPushAfterReply(User user, Reply reply, Post post, Long id) {
        if(!AuthorCertification.certification(user.getUsername(), post.getAuthor().getUsername())){
            if(subscribeNotificationRedisUseCase.isSubscribe(post.getAuthor().getId(), "reply")){
                FCMPushDTO pushDTO = fcmUtil.sendPushForReply(user, reply.getContent());
                FCMToken token = fcmUtil.getAuthorToken(post.getAuthor());
                sendPushForAuthor(pushDTO, token, post.getAuthor(), id);
            }
        }
    }

    @Override
    public void sendPushAfterReplyComment(User user, ReplyComment replyComment, Post post, Long id) {
        if(!AuthorCertification.certification(user.getUsername(), post.getAuthor().getUsername())){
            if(subscribeNotificationRedisUseCase.isSubscribe(post.getAuthor().getId(), "reply")){
                FCMPushDTO pushDTO = fcmUtil.sendPushForReply(user, replyComment.getContent());
                FCMToken token = fcmUtil.getAuthorToken(post.getAuthor());
                sendPushForAuthor(pushDTO, token, post.getAuthor(), id);
            }
        }
    }
}
