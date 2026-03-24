package kr.co.F1FS.app.domain.reply.infrastructure.repository.dsl;

import kr.co.F1FS.app.domain.reply.domain.ReplyComment;
import kr.co.F1FS.app.domain.reply.presentation.dto.replyComment.ResponseReplyCommentListDTO;

import java.util.List;
import java.util.Optional;

public interface ReplyCommentDSLRepository {
    Optional<ReplyComment> findById(Long id);
    List<ResponseReplyCommentListDTO> findAllByReply(Long replyId);
}
