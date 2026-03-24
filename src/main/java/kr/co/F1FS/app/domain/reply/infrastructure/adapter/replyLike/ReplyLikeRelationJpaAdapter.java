package kr.co.F1FS.app.domain.reply.infrastructure.adapter.replyLike;

import kr.co.F1FS.app.domain.reply.application.port.out.replyLike.ReplyLikeRelationJpaPort;
import kr.co.F1FS.app.domain.reply.domain.ReplyLikeRelation;
import kr.co.F1FS.app.domain.reply.infrastructure.repository.ReplyLikeRelationRepository;
import kr.co.F1FS.app.domain.reply.infrastructure.repository.dsl.ReplyLikeRelationDSLRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReplyLikeRelationJpaAdapter implements ReplyLikeRelationJpaPort {
    private final ReplyLikeRelationRepository replyLikeRelationRepository;
    private final ReplyLikeRelationDSLRepository replyLikeRelationDSLRepository;

    @Override
    public ReplyLikeRelation save(ReplyLikeRelation relation) {
        return replyLikeRelationRepository.save(relation);
    }

    @Override
    public ReplyLikeRelation findByUserAndReply(Long userId, Long replyId) {
        return replyLikeRelationDSLRepository.findByUserAndReply(userId, replyId);
    }

    @Override
    public boolean existsByUserAndReply(Long userId, Long replyId) {
        return replyLikeRelationDSLRepository.existsByUserAndReply(userId, replyId);
    }

    @Override
    public void delete(ReplyLikeRelation relation) {
        replyLikeRelationRepository.delete(relation);
    }
}
