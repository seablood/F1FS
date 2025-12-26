package kr.co.F1FS.app.domain.reply.application.service.replyComment;

import kr.co.F1FS.app.domain.reply.application.port.in.replyComment.DeleteReplyCommentUseCase;
import kr.co.F1FS.app.domain.reply.application.port.out.replyComment.ReplyCommentJpaPort;
import kr.co.F1FS.app.domain.reply.domain.ReplyComment;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.global.util.exception.reply.ReplyCommentException;
import kr.co.F1FS.app.global.util.exception.reply.ReplyCommentExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteReplyCommentService implements DeleteReplyCommentUseCase {
    private final ReplyCommentJpaPort replyCommentJpaPort;
    private final ReplyCommentDomainService replyCommentDomainService;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    public void delete(User user, ReplyComment replyComment) {
        if(!replyCommentDomainService.certification(user, replyComment)){
            throw new ReplyCommentException(ReplyCommentExceptionType.NOT_AUTHORITY_DELETE_REPLY_COMMENT);
        }

        cacheEvictUtil.evictCachingReply(replyComment.getReply().getPost());
        replyCommentJpaPort.delete(replyComment);
    }
}
