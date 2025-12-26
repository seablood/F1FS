package kr.co.F1FS.app.domain.reply.application.service.replyLike;

import kr.co.F1FS.app.domain.reply.application.mapper.ReplyMapper;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.reply.domain.ReplyLikeRelation;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyLikeRelationDomainService {
    private final ReplyMapper replyMapper;

    public ReplyLikeRelation createEntity(User user, Reply reply){
        return replyMapper.toReplyLikeRelation(user, reply);
    }
}
