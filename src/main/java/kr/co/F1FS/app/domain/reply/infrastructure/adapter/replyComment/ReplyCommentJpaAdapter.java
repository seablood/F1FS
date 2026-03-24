package kr.co.F1FS.app.domain.reply.infrastructure.adapter.replyComment;

import kr.co.F1FS.app.domain.reply.application.port.out.replyComment.ReplyCommentJpaPort;
import kr.co.F1FS.app.domain.reply.domain.ReplyComment;
import kr.co.F1FS.app.domain.reply.infrastructure.repository.ReplyCommentRepository;
import kr.co.F1FS.app.domain.reply.infrastructure.repository.dsl.ReplyCommentDSLRepository;
import kr.co.F1FS.app.domain.reply.presentation.dto.replyComment.ResponseReplyCommentListDTO;
import kr.co.F1FS.app.global.util.exception.reply.ReplyCommentException;
import kr.co.F1FS.app.global.util.exception.reply.ReplyCommentExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReplyCommentJpaAdapter implements ReplyCommentJpaPort {
    private final ReplyCommentRepository replyCommentRepository;
    private final ReplyCommentDSLRepository replyCommentDSLRepository;

    @Override
    public ReplyComment save(ReplyComment replyComment) {
        return replyCommentRepository.save(replyComment);
    }

    @Override
    public ReplyComment saveAndFlush(ReplyComment replyComment) {
        return replyCommentRepository.saveAndFlush(replyComment);
    }

    @Override
    public ReplyComment findByIdWithJoin(Long id) {
        return replyCommentDSLRepository.findById(id)
                .orElseThrow(() -> new ReplyCommentException(ReplyCommentExceptionType.REPLY_COMMENT_NOT_FOUND));
    }

    @Override
    public List<ResponseReplyCommentListDTO> findAllByReply(Long replyId) {
        return replyCommentDSLRepository.findAllByReply(replyId);
    }

    @Override
    public void delete(ReplyComment replyComment) {
        replyCommentRepository.delete(replyComment);
    }
}
