package kr.co.F1FS.app.domain.reply.application.service.replyLike;

import kr.co.F1FS.app.domain.reply.application.port.in.replyLike.QueryReplyLikeRelationUseCase;
import kr.co.F1FS.app.domain.reply.application.port.out.replyLike.ReplyLikeRelationJpaPort;
import kr.co.F1FS.app.domain.reply.domain.ReplyLikeRelation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryReplyLikeRelationService implements QueryReplyLikeRelationUseCase {
    private final ReplyLikeRelationJpaPort relationJpaPort;

    @Override
    public ReplyLikeRelation findByUserAndReply(Long userId, Long replyId) {
        return relationJpaPort.findByUserAndReply(userId, replyId);
    }
}
