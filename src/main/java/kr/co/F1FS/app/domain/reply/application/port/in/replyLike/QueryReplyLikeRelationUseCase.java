package kr.co.F1FS.app.domain.reply.application.port.in.replyLike;

import kr.co.F1FS.app.domain.reply.domain.ReplyLikeRelation;

public interface QueryReplyLikeRelationUseCase {
    ReplyLikeRelation findByUserAndReply(Long userId, Long replyId);
}
