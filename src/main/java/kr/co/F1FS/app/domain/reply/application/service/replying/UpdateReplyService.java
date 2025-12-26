package kr.co.F1FS.app.domain.reply.application.service.replying;

import kr.co.F1FS.app.domain.reply.application.mapper.ReplyMapper;
import kr.co.F1FS.app.domain.reply.application.port.in.replying.UpdateReplyUseCase;
import kr.co.F1FS.app.domain.reply.application.port.out.replying.ReplyJpaPort;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.reply.presentation.dto.replying.ModifyReplyDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.application.service.ValidationService;
import kr.co.F1FS.app.global.presentation.dto.reply.ResponseReplyDTO;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.global.util.exception.reply.ReplyException;
import kr.co.F1FS.app.global.util.exception.reply.ReplyExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateReplyService implements UpdateReplyUseCase {
    private final ReplyJpaPort replyJpaPort;
    private final ReplyDomainService replyDomainService;
    private final ReplyMapper replyMapper;
    private final ValidationService validationService;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    public ResponseReplyDTO modify(Reply reply, User user, ModifyReplyDTO dto) {
        cacheEvictUtil.evictCachingReply(reply.getPost());

        if(!replyDomainService.certification(user, reply)){
            throw new ReplyException(ReplyExceptionType.NOT_AUTHORITY_UPDATE_REPLY);
        }

        replyDomainService.modify(dto, reply);
        validationService.checkValid(reply);
        replyJpaPort.saveAndFlush(reply);
        return replyMapper.toResponseReplyDTO(reply);
    }

    @Override
    public void increaseLikeNum(Reply reply) {
        replyDomainService.increaseLikeNum(reply);

        replyJpaPort.saveAndFlush(reply);
    }

    @Override
    public void decreaseLikeNum(Reply reply) {
        replyDomainService.decreaseLikeNum(reply);

        replyJpaPort.saveAndFlush(reply);
    }
}
