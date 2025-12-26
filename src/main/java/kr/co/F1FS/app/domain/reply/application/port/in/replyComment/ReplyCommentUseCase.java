package kr.co.F1FS.app.domain.reply.application.port.in.replyComment;

import kr.co.F1FS.app.domain.reply.domain.ReplyComment;
import kr.co.F1FS.app.domain.reply.presentation.dto.replyComment.CreateReplyCommentDTO;
import kr.co.F1FS.app.domain.reply.presentation.dto.replyComment.ModifyReplyCommentDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.reply.ResponseReplyCommentDTO;

public interface ReplyCommentUseCase {
    ReplyComment save(CreateReplyCommentDTO dto, User user, Long id);
    ResponseReplyCommentDTO modify(ModifyReplyCommentDTO dto, User user, Long replyCommentId);
    void delete(Long replyCommentId, User user);
}
