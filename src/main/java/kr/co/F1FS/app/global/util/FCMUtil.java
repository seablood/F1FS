package kr.co.F1FS.app.global.util;

import kr.co.F1FS.app.domain.follow.application.port.in.user.QueryFollowUserUseCase;
import kr.co.F1FS.app.domain.form.domain.PostRoomDeleteForm;
import kr.co.F1FS.app.domain.form.domain.PostRoomForm;
import kr.co.F1FS.app.domain.notification.application.port.in.fcmToken.QueryFCMTokenUseCase;
import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import kr.co.F1FS.app.domain.postRoomSuspension.domain.PostRoomSuspension;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserIdDTO;
import kr.co.F1FS.app.global.presentation.dto.notification.FCMPushDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FCMUtil {
    private final QueryFollowUserUseCase queryFollowUserUseCase;
    private final QueryFCMTokenUseCase queryFCMTokenUseCase;

    public FCMPushDTO sendPushForPost(User author, String title){
        FCMPushDTO pushDTO = FCMPushDTO.builder()
                .title("새로운 게시글")
                .content(author.getNickname()+"님이 새로운 글을 작성하였습니다! : "+title)
                .topics(Topic.POST)
                .build();

        return pushDTO;
    }

    public FCMPushDTO sendPushForReply(User user, String content){
        FCMPushDTO pushDTO = FCMPushDTO.builder()
                .title("새로운 댓글")
                .content(user.getNickname()+"님이 댓글을 작성하였습니다! : "+content)
                .topics(Topic.REPLY)
                .build();

        return pushDTO;
    }

    public FCMPushDTO sendPushForLike(User user){
        FCMPushDTO pushDTO = FCMPushDTO.builder()
                .title("본인의 게시글이 추천되었습니다!!")
                .content(user.getNickname()+"님이 게시글을 좋아합니다.")
                .topics(Topic.LIKE)
                .build();

        return pushDTO;
    }

    public FCMPushDTO sendPushForNote(User user){
        FCMPushDTO pushDTO = FCMPushDTO.builder()
                .title("새로운 쪽지가 도착했습니다.")
                .content(user.getNickname()+"님이 족지를 보냈습니다.")
                .topics(Topic.NOTE)
                .build();

        return pushDTO;
    }

    public FCMPushDTO sendPushForPostRoomFormSave(User user, PostRoomForm postRoomForm){
        FCMPushDTO pushDTO = FCMPushDTO.builder()
                .title("게시판 신청이 완료되었습니다.")
                .content(user.getNickname()+"님이 신청한 게시판 : "+postRoomForm.getRoomTitle())
                .topics(Topic.POST_ROOM)
                .build();

        return pushDTO;
    }

    public FCMPushDTO sendPushForPostRoomDeleteFormSave(User user, PostRoomDeleteForm deleteForm){
        FCMPushDTO pushDTO = FCMPushDTO.builder()
                .title("게시판 삭제 신청이 완료되었습니다.")
                .content(user.getNickname()+"님이 신청한 게시판 : "+deleteForm.getPostRoom().getRoomTitle())
                .topics(Topic.POST_ROOM)
                .build();

        return pushDTO;
    }

    public FCMPushDTO sendPushForPostRoomFormConfirmed(User user, PostRoomForm postRoomForm){
        FCMPushDTO pushDTO = FCMPushDTO.builder()
                .title("게시판 승인 여부가 결정되었습니다.")
                .content(user.getNickname()+"님이 신청한 게시판의 승인 여부가 결정되었습니다. : "+postRoomForm.getRoomTitle())
                .topics(Topic.POST_ROOM)
                .build();

        return pushDTO;
    }

    public FCMPushDTO sendPushForPostRoomDeleteFormConfirmed(User user, PostRoomDeleteForm deleteForm){
        FCMPushDTO pushDTO = FCMPushDTO.builder()
                .title("게시판 삭제 승인 여부가 결정되었습니다.")
                .content(user.getNickname()+"님이 삭제 신청한 게시판의 승인 여부가 결정되었습니다. : "+deleteForm.getPostRoom().getRoomTitle())
                .topics(Topic.POST_ROOM)
                .build();

        return pushDTO;
    }

    public FCMPushDTO sendPushForPostRoomSuspensionSave(PostRoomSuspension postRoomSuspension){
        User user = postRoomSuspension.getSuspendUser();
        FCMPushDTO pushDTO = FCMPushDTO.builder()
                .title("게시판 이용 금지 안내")
                .content(user.getNickname()+"님의 게시판 이용이 금지되었습니다. : "+postRoomSuspension.getPostRoom().getRoomTitle())
                .topics(Topic.POST_ROOM)
                .build();

        return pushDTO;
    }

    public FCMPushDTO sendPushForPostRoomSuspensionDelete(User suspendUser, PostRoom postRoom){
        FCMPushDTO pushDTO = FCMPushDTO.builder()
                .title("게시판 이용 금지 해제 안내")
                .content(suspendUser.getNickname()+"님의 게시판 이용 금지가 해제되었습니다. : "+postRoom.getRoomTitle())
                .topics(Topic.POST_ROOM)
                .build();

        return pushDTO;
    }

    public FCMToken getAuthorToken(User author){
        return queryFCMTokenUseCase.findByUserIdOrNull(author.getId());
    }

    public List<FCMToken> getFollowerToken(User author){
        List<ResponseUserIdDTO> followerList = queryFollowUserUseCase.findByFolloweeUserIdForDTO(author.getId());
        List<FCMToken> tokens = new ArrayList<>();

        for (ResponseUserIdDTO followerId : followerList){
            FCMToken notification = queryFCMTokenUseCase.findByUserIdOrNull(followerId.getId());
            if(notification != null) tokens.add(notification);
        }

        return tokens;
    }
}
