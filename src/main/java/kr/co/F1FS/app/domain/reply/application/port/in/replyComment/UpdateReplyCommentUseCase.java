package kr.co.F1FS.app.domain.reply.application.port.in.replyComment;

import kr.co.F1FS.app.domain.reply.domain.ReplyComment;
import kr.co.F1FS.app.domain.reply.presentation.dto.replyComment.ModifyReplyCommentDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.reply.ResponseReplyCommentDTO;

public interface UpdateReplyCommentUseCase {
    ResponseReplyCommentDTO modify(ModifyReplyCommentDTO dto, User user, ReplyComment replyComment);
}
