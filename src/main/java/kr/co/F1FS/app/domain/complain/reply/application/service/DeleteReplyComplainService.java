package kr.co.F1FS.app.domain.complain.reply.application.service;

import kr.co.F1FS.app.domain.complain.reply.application.port.in.DeleteReplyComplainUseCase;
import kr.co.F1FS.app.domain.complain.reply.application.port.out.ReplyComplainJpaPort;
import kr.co.F1FS.app.domain.complain.reply.domain.ReplyComplain;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteReplyComplainService implements DeleteReplyComplainUseCase {
    private final ReplyComplainJpaPort replyComplainJpaPort;
    private final ReplyComplainDomainService replyComplainDomainService;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    public void delete(ReplyComplain replyComplain, User user) {
        cacheEvictUtil.evictCachingReplyComplain(replyComplain);

        if(replyComplainDomainService.certification(replyComplain, user)){
            replyComplainJpaPort.delete(replyComplain);
        }
    }
}
