package kr.co.F1FS.app.domain.reply.application.service.replyLike;

import kr.co.F1FS.app.domain.reply.application.port.in.replyLike.CheckReplyLikeRelationUseCase;
import kr.co.F1FS.app.domain.reply.application.port.out.replyLike.ReplyLikeRelationJpaPort;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckReplyLikeRelationService implements CheckReplyLikeRelationUseCase {
    private final ReplyLikeRelationJpaPort relationJpaPort;

    @Override
    public boolean existsReplyLikeRelationByUserAndReply(User user, Reply reply) {
        return relationJpaPort.existsReplyLikeRelationByUserAndReply(user, reply);
    }
}
