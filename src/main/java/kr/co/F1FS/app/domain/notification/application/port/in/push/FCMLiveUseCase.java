package kr.co.F1FS.app.domain.notification.application.port.in.push;

import kr.co.F1FS.app.domain.form.domain.PostRoomDeleteForm;
import kr.co.F1FS.app.domain.form.domain.PostRoomForm;
import kr.co.F1FS.app.domain.notification.domain.FCMToken;
import kr.co.F1FS.app.domain.notification.domain.NotificationRedis;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import kr.co.F1FS.app.domain.postRoomSuspension.domain.PostRoomSuspension;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.reply.domain.ReplyComment;
import kr.co.F1FS.app.global.presentation.dto.notification.FCMPushDTO;
import kr.co.F1FS.app.domain.user.domain.User;

import java.util.List;

public interface FCMLiveUseCase {
    NotificationRedis sendPushForLiveInfo(FCMPushDTO dto);
    void sendPushForAuthor(FCMPushDTO dto, FCMToken token, User user, Long contentId);
    void sendPushForFollow(FCMPushDTO dto, List<FCMToken> tokens, Long contentId);
    void sendPushAfterPosting(Post post, User author);
    void sendPushAfterPostLike(User user, Post post, Long id);
    void sendPushAfterReply(User user, Reply reply, Post post, Long id);
    void sendPushAfterReplyComment(User user, ReplyComment replyComment, Post post, Long id);
    void sendPushAfterPostRoomSave(User user, PostRoomForm postRoomForm, Long id);
    void sendPushAfterPostRoomDeleteFormSave(User user, PostRoomDeleteForm deleteForm, Long id);
    void sendPushAfterPostRoomConfirmed(User user, PostRoomForm postRoomForm, Long id);
    void sendPushAfterPostRoomDeleteConfirmed(User user, PostRoomDeleteForm deleteForm, Long id);
    void sendPushAfterPostRoomSuspensionSave(PostRoomSuspension postRoomSuspension, Long roomId);
    void sendPushAfterPostRoomSuspensionDelete(User suspendUser, PostRoom postRoom,  Long roomId);
}
