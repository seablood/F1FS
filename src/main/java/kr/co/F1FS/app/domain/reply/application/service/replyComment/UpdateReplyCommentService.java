package kr.co.F1FS.app.domain.reply.application.service.replyComment;

import kr.co.F1FS.app.domain.reply.application.mapper.ReplyMapper;
import kr.co.F1FS.app.domain.reply.application.port.in.replyComment.UpdateReplyCommentUseCase;
import kr.co.F1FS.app.domain.reply.application.port.out.replyComment.ReplyCommentJpaPort;
import kr.co.F1FS.app.domain.reply.domain.ReplyComment;
import kr.co.F1FS.app.domain.reply.presentation.dto.replyComment.ModifyReplyCommentDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.application.service.ValidationService;
import kr.co.F1FS.app.global.presentation.dto.reply.ResponseReplyCommentDTO;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.global.util.exception.reply.ReplyCommentException;
import kr.co.F1FS.app.global.util.exception.reply.ReplyCommentExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateReplyCommentService implements UpdateReplyCommentUseCase {
    private final ReplyCommentJpaPort replyCommentJpaPort;
    private final ReplyCommentDomainService replyCommentDomainService;
    private final ReplyMapper replyMapper;
    private final ValidationService validationService;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    public ResponseReplyCommentDTO modify(ModifyReplyCommentDTO dto, User user, ReplyComment replyComment) {
        cacheEvictUtil.evictCachingReply(replyComment.getReply().getPost());

        if(!replyCommentDomainService.certification(user, replyComment)){
            throw new ReplyCommentException(ReplyCommentExceptionType.NOT_AUTHORITY_UPDATE_REPLY_COMMENT);
        }

        replyCommentDomainService.modify(dto, replyComment);
        validationService.checkValid(replyComment);
        replyCommentJpaPort.saveAndFlush(replyComment);

        return replyMapper.toResponseReplyCommentDTO(replyComment);
    }
}
