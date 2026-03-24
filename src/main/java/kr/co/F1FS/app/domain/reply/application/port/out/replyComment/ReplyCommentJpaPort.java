package kr.co.F1FS.app.domain.reply.application.port.out.replyComment;

import kr.co.F1FS.app.domain.reply.domain.ReplyComment;
import kr.co.F1FS.app.domain.reply.presentation.dto.replyComment.ResponseReplyCommentListDTO;

import java.util.List;

public interface ReplyCommentJpaPort {
    ReplyComment save(ReplyComment replyComment);
    ReplyComment saveAndFlush(ReplyComment replyComment);
    ReplyComment findByIdWithJoin(Long id);
    List<ResponseReplyCommentListDTO> findAllByReply(Long replyId);
    void delete(ReplyComment replyComment);
}
