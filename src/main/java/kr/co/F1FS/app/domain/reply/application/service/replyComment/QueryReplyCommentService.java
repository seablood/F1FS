package kr.co.F1FS.app.domain.reply.application.service.replyComment;

import kr.co.F1FS.app.domain.reply.application.port.in.replyComment.QueryReplyCommentUseCase;
import kr.co.F1FS.app.domain.reply.application.port.out.replyComment.ReplyCommentJpaPort;
import kr.co.F1FS.app.domain.reply.domain.ReplyComment;
import kr.co.F1FS.app.domain.reply.presentation.dto.replyComment.ResponseReplyCommentListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryReplyCommentService implements QueryReplyCommentUseCase {
    private final ReplyCommentJpaPort replyCommentJpaPort;

    @Override
    public ReplyComment findByIdWithJoin(Long id) {
        return replyCommentJpaPort.findByIdWithJoin(id);
    }

    @Override
    public List<ResponseReplyCommentListDTO> findAllByReplyForDTO(Long replyId) {
        return replyCommentJpaPort.findAllByReply(replyId);
    }
}
