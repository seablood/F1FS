package kr.co.F1FS.app.domain.complain.reply.application.port.in;

import kr.co.F1FS.app.domain.complain.reply.domain.ReplyComplain;
import kr.co.F1FS.app.domain.complain.reply.presentation.dto.CreateReplyComplainDTO;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.user.domain.User;

public interface CreateReplyComplainUseCase {
    ReplyComplain save(Reply reply, User user, CreateReplyComplainDTO dto);
}
