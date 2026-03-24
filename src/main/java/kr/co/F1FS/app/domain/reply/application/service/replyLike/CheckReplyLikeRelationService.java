package kr.co.F1FS.app.domain.reply.application.service.replyLike;

import kr.co.F1FS.app.domain.reply.application.port.in.replyLike.CheckReplyLikeRelationUseCase;
import kr.co.F1FS.app.domain.reply.application.port.out.replyLike.ReplyLikeRelationJpaPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckReplyLikeRelationService implements CheckReplyLikeRelationUseCase {
    private final ReplyLikeRelationJpaPort relationJpaPort;

    @Override
    public boolean existsByUserAndReply(Long userId, Long replyId) {
        return relationJpaPort.existsByUserAndReply(userId, replyId);
    }
}
