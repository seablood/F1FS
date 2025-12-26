package kr.co.F1FS.app.domain.reply.application.port.in.replyComment;

import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.reply.domain.ReplyComment;
import kr.co.F1FS.app.domain.reply.presentation.dto.replyComment.CreateReplyCommentDTO;
import kr.co.F1FS.app.domain.user.domain.User;

public interface CreateReplyCommentUseCase {
    ReplyComment save(CreateReplyCommentDTO dto, User user, Reply reply);
}
