package kr.co.F1FS.app.domain.reply.application.port.in.replyLike;

import kr.co.F1FS.app.domain.user.domain.User;

public interface ReplyLikeRelationUseCase {
    void toggle(User user, Long id);
}
