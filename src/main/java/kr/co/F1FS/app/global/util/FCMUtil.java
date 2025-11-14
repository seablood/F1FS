package kr.co.F1FS.app.global.util;

import kr.co.F1FS.app.domain.follow.application.port.in.FollowUserUseCase;
import kr.co.F1FS.app.domain.notification.application.port.in.FCMTokenUseCase;
import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserIdDTO;
import kr.co.F1FS.app.domain.notification.presentation.dto.FCMPushDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FCMUtil {
    private final FollowUserUseCase followUserUseCase;
    private final FCMTokenUseCase tokenUseCase;

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

    public FCMPushDTO sendPushForNote(User user){
        FCMPushDTO pushDTO = FCMPushDTO.builder()
                .topic("note")
                .title("새로운 쪽지가 도착했습니다.")
                .content(user.getNickname()+"님이 족지를 보냈습니다.")
                .build();

        return pushDTO;
    }

    public FCMToken getAuthorToken(User author){
        return tokenUseCase.findByUserIdOrNull(author.getId());
    }

    public List<FCMToken> getFollowerToken(User author){
        List<ResponseUserIdDTO> followerList = followUserUseCase.findFollowersNotDTO(author);
        List<FCMToken> tokens = new ArrayList<>();

        for (ResponseUserIdDTO followerId : followerList){
            FCMToken notification = tokenUseCase.findByUserIdOrNull(followerId.getId());
            if(notification != null) tokens.add(notification);
        }

        return tokens;
    }
}
