package kr.co.F1FS.app.domain.reply.application.port.in.replying;

import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.user.domain.User;

public interface DeleteReplyUseCase {
    void delete(Reply reply, User user);
}
