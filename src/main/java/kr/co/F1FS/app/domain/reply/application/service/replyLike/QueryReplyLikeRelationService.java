package kr.co.F1FS.app.domain.reply.application.service.replyLike;

import kr.co.F1FS.app.domain.reply.application.port.in.replyLike.QueryReplyLikeRelationUseCase;
import kr.co.F1FS.app.domain.reply.application.port.out.replyLike.ReplyLikeRelationJpaPort;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.reply.domain.ReplyLikeRelation;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryReplyLikeRelationService implements QueryReplyLikeRelationUseCase {
    private final ReplyLikeRelationJpaPort relationJpaPort;

    @Override
    public ReplyLikeRelation findReplyLikeRelationByUserAndReply(User user, Reply reply) {
        return relationJpaPort.findReplyLikeRelationByUserAndReply(user, reply);
    }
}
