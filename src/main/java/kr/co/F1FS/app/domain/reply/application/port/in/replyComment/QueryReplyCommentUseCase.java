package kr.co.F1FS.app.domain.reply.application.port.in.replyComment;

import kr.co.F1FS.app.domain.reply.domain.ReplyComment;
import kr.co.F1FS.app.domain.reply.presentation.dto.replyComment.ResponseReplyCommentListDTO;

import java.util.List;

public interface QueryReplyCommentUseCase {
    ReplyComment findByIdWithJoin(Long id);
    List<ResponseReplyCommentListDTO> findAllByReplyForDTO(Long replyId);
}
