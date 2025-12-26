package kr.co.F1FS.app.domain.reply.application.port.in.replyLike;

import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.user.domain.User;

public interface CreateReplyLikeRelationUseCase {
    void save(User user, Reply reply);
}
