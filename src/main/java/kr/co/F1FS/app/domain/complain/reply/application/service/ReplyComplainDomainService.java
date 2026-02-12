package kr.co.F1FS.app.domain.complain.reply.application.service;

import kr.co.F1FS.app.domain.complain.reply.application.mapper.ReplyComplainMapper;
import kr.co.F1FS.app.domain.complain.reply.domain.ReplyComplain;
import kr.co.F1FS.app.domain.complain.reply.presentation.dto.CreateReplyComplainDTO;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.AuthorCertification;
import kr.co.F1FS.app.global.util.exception.reply.ReplyException;
import kr.co.F1FS.app.global.util.exception.reply.ReplyExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyComplainDomainService {
    private final ReplyComplainMapper replyComplainMapper;

    public ReplyComplain createEntity(Reply reply, User user, CreateReplyComplainDTO dto){
        return replyComplainMapper.toReplyComplain(reply, user, dto);
    }

    public boolean certification(ReplyComplain replyComplain, User user){
        if(AuthorCertification.certification(user.getUsername(), replyComplain.getFromUser().getUsername())){
            return true;
        }else {
            throw new ReplyException(ReplyExceptionType.NOT_AUTHORITY_DELETE_REPLY_COMPLAIN);
        }
    }
}
