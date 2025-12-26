package kr.co.F1FS.app.domain.reply.application.service.replyLike;

import kr.co.F1FS.app.domain.reply.application.port.in.replyLike.CreateReplyLikeRelationUseCase;
import kr.co.F1FS.app.domain.reply.application.port.out.replyLike.ReplyLikeRelationJpaPort;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.reply.domain.ReplyLikeRelation;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateReplyLikeRelationService implements CreateReplyLikeRelationUseCase {
    private final ReplyLikeRelationJpaPort relationJpaPort;
    private final ReplyLikeRelationDomainService relationDomainService;

    @Override
    public void save(User user, Reply reply) {
        ReplyLikeRelation relation = relationDomainService.createEntity(user, reply);

        relationJpaPort.save(relation);
    }
}
