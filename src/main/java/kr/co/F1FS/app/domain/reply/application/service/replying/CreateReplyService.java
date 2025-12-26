package kr.co.F1FS.app.domain.reply.application.service.replying;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.reply.application.port.in.replying.CreateReplyUseCase;
import kr.co.F1FS.app.domain.reply.application.port.out.replying.ReplyJpaPort;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.reply.presentation.dto.replying.CreateReplyDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.application.service.ValidationService;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateReplyService implements CreateReplyUseCase {
    private final ReplyJpaPort replyJpaPort;
    private final ReplyDomainService replyDomainService;
    private final ValidationService validationService;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    public Reply save(CreateReplyDTO dto, User user, Post post) {
        Reply reply = replyDomainService.createEntity(dto, user, post);
        validationService.checkValid(reply);
        cacheEvictUtil.evictCachingReply(post);

        return replyJpaPort.save(reply);
    }
}
