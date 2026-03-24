package kr.co.F1FS.app.domain.reply.application.port.out.replyLike;

import kr.co.F1FS.app.domain.reply.domain.ReplyLikeRelation;

public interface ReplyLikeRelationJpaPort {
    ReplyLikeRelation save(ReplyLikeRelation relation);
    ReplyLikeRelation findByUserAndReply(Long userId, Long replyId);
    boolean existsByUserAndReply(Long userId, Long replyId);
    void delete(ReplyLikeRelation relation);
}
