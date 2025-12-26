package kr.co.F1FS.app.domain.reply.application.service.replyLike;

import kr.co.F1FS.app.domain.reply.application.port.in.replyLike.DeleteReplyLikeRelationUseCase;
import kr.co.F1FS.app.domain.reply.application.port.out.replyLike.ReplyLikeRelationJpaPort;
import kr.co.F1FS.app.domain.reply.domain.ReplyLikeRelation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteReplyLikeRelationService implements DeleteReplyLikeRelationUseCase {
    private final ReplyLikeRelationJpaPort relationJpaPort;

    @Override
    public void delete(ReplyLikeRelation relation) {
        relationJpaPort.delete(relation);
    }
}
