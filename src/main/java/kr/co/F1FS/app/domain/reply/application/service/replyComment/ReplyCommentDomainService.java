package kr.co.F1FS.app.domain.reply.application.service.replyComment;

import kr.co.F1FS.app.domain.reply.application.mapper.ReplyMapper;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.reply.domain.ReplyComment;
import kr.co.F1FS.app.domain.reply.presentation.dto.replyComment.CreateReplyCommentDTO;
import kr.co.F1FS.app.domain.reply.presentation.dto.replyComment.ModifyReplyCommentDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.AuthorCertification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyCommentDomainService {
    private final ReplyMapper replyMapper;

    public ReplyComment createEntity(CreateReplyCommentDTO dto, User user, Reply reply){
        return replyMapper.toReplyComment(dto, user, reply);
    }

    public void modify(ModifyReplyCommentDTO dto, ReplyComment replyComment){
        replyComment.modify(dto.getContent());
    }

    public boolean certification(User user, ReplyComment replyComment){
        return AuthorCertification.certification(user.getUsername(), replyComment.getUser().getUsername());
    }
}
