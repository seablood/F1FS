package kr.co.F1FS.app.domain.reply.application.port.out.replyLike;

import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.reply.domain.ReplyLikeRelation;
import kr.co.F1FS.app.domain.user.domain.User;

public interface ReplyLikeRelationJpaPort {
    ReplyLikeRelation save(ReplyLikeRelation relation);
    ReplyLikeRelation findReplyLikeRelationByUserAndReply(User user, Reply reply);
    boolean existsReplyLikeRelationByUserAndReply(User user, Reply reply);
    void delete(ReplyLikeRelation relation);
}
