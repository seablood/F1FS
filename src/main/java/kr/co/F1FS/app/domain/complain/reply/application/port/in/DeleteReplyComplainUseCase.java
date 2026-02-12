package kr.co.F1FS.app.domain.complain.reply.application.port.in;

import kr.co.F1FS.app.domain.complain.reply.domain.ReplyComplain;
import kr.co.F1FS.app.domain.user.domain.User;

public interface DeleteReplyComplainUseCase {
    void delete(ReplyComplain replyComplain, User user);
}
