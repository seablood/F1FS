package kr.co.F1FS.app.global.util;

import kr.co.F1FS.app.application.follow.FollowUserService;
import kr.co.F1FS.app.domain.model.rdb.FCMNotification;
import kr.co.F1FS.app.domain.model.rdb.User;
import kr.co.F1FS.app.domain.repository.rdb.fcm.FCMNotificationRepository;
import kr.co.F1FS.app.global.util.exception.user.UserException;
import kr.co.F1FS.app.global.util.exception.user.UserExceptionType;
import kr.co.F1FS.app.presentation.fcm.dto.FCMPushDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FCMUtil {
    private final FollowUserService followUserService;
    private final FCMNotificationRepository notificationRepository;

    public FCMPushDTO sendPushForPost(User author, String title){
        FCMPushDTO pushDTO = FCMPushDTO.builder()
                .topic("post")
                .title("새로운 게시글")
                .content(author.getNickname()+"님이 새로운 글을 작성하였습니다! : "+title)
                .build();

        return pushDTO;
    }

    public FCMPushDTO sendPushForReply(User user, String content){
        FCMPushDTO pushDTO = FCMPushDTO.builder()
                .topic("reply")
                .title("새로운 댓글")
                .content(user.getNickname()+"님이 댓글을 작성하였습니다! : "+content)
                .build();

        return pushDTO;
    }

    public FCMPushDTO sendPushForLike(User user){
        FCMPushDTO pushDTO = FCMPushDTO.builder()
                .topic("like")
                .title("본인의 게시글이 추천되었습니다!!")
                .content(user.getNickname())
                .build();

        return pushDTO;
    }

    public FCMNotification getAuthorToken(User author){
        return notificationRepository.findByUserId(author.getId())
                .orElseThrow(() -> new UserException(UserExceptionType.TOKEN_NOT_FOUND));
    }

    public List<FCMNotification> getFollowerToken(User author){
        List<User> followerList = followUserService.findFollowersNotDTO(author);
        List<FCMNotification> tokens = new ArrayList<>();

        for (User follower : followerList){
            FCMNotification notification = notificationRepository.findByUserId(follower.getId())
                    .orElse(null);
            if(notification != null) tokens.add(notification);
        }

        return tokens;
    }
}
