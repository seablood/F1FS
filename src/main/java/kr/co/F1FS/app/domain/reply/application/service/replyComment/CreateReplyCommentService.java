package kr.co.F1FS.app.domain.reply.application.service.replyComment;

import kr.co.F1FS.app.domain.reply.application.port.in.replyComment.CreateReplyCommentUseCase;
import kr.co.F1FS.app.domain.reply.application.port.out.replyComment.ReplyCommentJpaPort;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.reply.domain.ReplyComment;
import kr.co.F1FS.app.domain.reply.presentation.dto.replyComment.CreateReplyCommentDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.application.service.ValidationService;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateReplyCommentService implements CreateReplyCommentUseCase {
    private final ReplyCommentJpaPort replyCommentJpaPort;
    private final ReplyCommentDomainService replyCommentDomainService;
    private final ValidationService validationService;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    public ReplyComment save(CreateReplyCommentDTO dto, User user, Reply reply) {
        ReplyComment comment = replyCommentDomainService.createEntity(dto, user, reply);
        validationService.checkValid(comment);
        cacheEvictUtil.evictCachingReply(reply.getPost());

        return replyCommentJpaPort.save(comment);
    }
}
