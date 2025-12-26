package kr.co.F1FS.app.domain.reply.application.port.in.replying;

import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.reply.presentation.dto.replying.ModifyReplyDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.reply.ResponseReplyDTO;

public interface UpdateReplyUseCase {
    ResponseReplyDTO modify(Reply reply, User user, ModifyReplyDTO dto);
    void increaseLikeNum(Reply reply);
    void decreaseLikeNum(Reply reply);
}
