package kr.co.F1FS.app.domain.reply.application.service.replying;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.reply.application.mapper.ReplyMapper;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.reply.presentation.dto.replying.CreateReplyDTO;
import kr.co.F1FS.app.domain.reply.presentation.dto.replying.ModifyReplyDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.AuthorCertification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyDomainService {
    private final ReplyMapper replyMapper;

    public Reply createEntity(CreateReplyDTO dto, User user, Post post){
        return replyMapper.toReply(dto, user, post);
    }

    public void modify(ModifyReplyDTO dto, Reply reply){
        reply.modify(dto.getContent());
    }

    public void increaseLikeNum(Reply reply){
        reply.increaseLikeNum();
    }

    public void decreaseLikeNum(Reply reply){
        reply.decreaseLikeNum();
    }

    public boolean certification(User user, Reply reply){
        return AuthorCertification.certification(user.getUsername(), reply.getUser().getUsername());
    }
}
