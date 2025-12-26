package kr.co.F1FS.app.domain.reply.application.port.in.replyComment;

import kr.co.F1FS.app.domain.reply.domain.ReplyComment;
import kr.co.F1FS.app.domain.user.domain.User;

public interface DeleteReplyCommentUseCase {
    void delete(User user, ReplyComment replyComment);
}
