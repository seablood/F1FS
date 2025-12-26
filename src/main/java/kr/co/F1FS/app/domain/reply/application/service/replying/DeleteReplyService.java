package kr.co.F1FS.app.domain.reply.application.service.replying;

import kr.co.F1FS.app.domain.reply.application.port.in.replying.DeleteReplyUseCase;
import kr.co.F1FS.app.domain.reply.application.port.out.replying.ReplyJpaPort;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.global.util.exception.reply.ReplyException;
import kr.co.F1FS.app.global.util.exception.reply.ReplyExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteReplyService implements DeleteReplyUseCase {
    private final ReplyJpaPort replyJpaPort;
    private final ReplyDomainService replyDomainService;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    public void delete(Reply reply, User user) {
        if(!replyDomainService.certification(user, reply)){
            throw new ReplyException(ReplyExceptionType.NOT_AUTHORITY_DELETE_REPLY);
        }
        cacheEvictUtil.evictCachingReply(reply.getPost());

        replyJpaPort.delete(reply);
    }
}
