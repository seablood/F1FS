package kr.co.F1FS.app.global.util;

import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.notification.infrastructure.repository.FCMTokenRepository;
import kr.co.F1FS.app.global.application.port.out.FCMUtilFollowUserPort;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserIdDTO;
import kr.co.F1FS.app.global.util.exception.user.UserException;
import kr.co.F1FS.app.global.util.exception.user.UserExceptionType;
import kr.co.F1FS.app.domain.notification.presentation.dto.FCMPushDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FCMUtil {
    private final FCMUtilFollowUserPort followUserPort;
    private final FCMTokenRepository notificationRepository;

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
                .content(user.getNickname()+"님이 게시글을 좋아합니다.")
                .build();

        return pushDTO;
    }

    public FCMToken getAuthorToken(User author){
        return notificationRepository.findByUserId(author.getId())
                .orElseThrow(() -> new UserException(UserExceptionType.TOKEN_NOT_FOUND));
    }

    public List<FCMToken> getFollowerToken(User author){
        List<ResponseUserIdDTO> followerList = followUserPort.findFollowersNotDTO(author);
        List<FCMToken> tokens = new ArrayList<>();

        for (ResponseUserIdDTO followerId : followerList){
            FCMToken notification = notificationRepository.findByUserId(followerId.getId())
                    .orElse(null);
            if(notification != null) tokens.add(notification);
        }

        return tokens;
    }
}
