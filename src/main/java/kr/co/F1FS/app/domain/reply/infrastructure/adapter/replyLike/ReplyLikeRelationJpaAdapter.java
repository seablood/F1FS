package kr.co.F1FS.app.domain.reply.infrastructure.adapter.replyLike;

import kr.co.F1FS.app.domain.reply.application.port.out.replyLike.ReplyLikeRelationJpaPort;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.reply.domain.ReplyLikeRelation;
import kr.co.F1FS.app.domain.reply.infrastructure.repository.ReplyLikeRelationRepository;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReplyLikeRelationJpaAdapter implements ReplyLikeRelationJpaPort {
    private final ReplyLikeRelationRepository replyLikeRelationRepository;

    @Override
    public ReplyLikeRelation save(ReplyLikeRelation relation) {
        return replyLikeRelationRepository.save(relation);
    }

    @Override
    public ReplyLikeRelation findReplyLikeRelationByUserAndReply(User user, Reply reply) {
        return replyLikeRelationRepository.findReplyLikeRelationByUserAndReply(user, reply);
    }

    @Override
    public boolean existsReplyLikeRelationByUserAndReply(User user, Reply reply) {
        return replyLikeRelationRepository.existsReplyLikeRelationByUserAndReply(user, reply);
    }

    @Override
    public void delete(ReplyLikeRelation relation) {
        replyLikeRelationRepository.delete(relation);
    }
}
