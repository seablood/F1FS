package kr.co.F1FS.app.domain.reply.infrastructure.repository.dsl;

import kr.co.F1FS.app.domain.reply.domain.ReplyLikeRelation;

public interface ReplyLikeRelationDSLRepository {
    ReplyLikeRelation findByUserAndReply(Long userId, Long replyId);
    boolean existsByUserAndReply(Long userId, Long replyId);
}
