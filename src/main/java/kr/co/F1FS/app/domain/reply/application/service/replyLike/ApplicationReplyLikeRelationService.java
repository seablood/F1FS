package kr.co.F1FS.app.domain.reply.application.service.replyLike;

import kr.co.F1FS.app.domain.reply.application.port.in.replyLike.CheckReplyLikeRelationUseCase;
import kr.co.F1FS.app.domain.reply.application.port.in.replyLike.CreateReplyLikeRelationUseCase;
import kr.co.F1FS.app.domain.reply.application.port.in.replyLike.QueryReplyLikeRelationUseCase;
import kr.co.F1FS.app.domain.reply.application.port.in.replying.QueryReplyUseCase;
import kr.co.F1FS.app.domain.reply.application.port.in.replyLike.ReplyLikeRelationUseCase;
import kr.co.F1FS.app.domain.reply.application.port.in.replying.UpdateReplyUseCase;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.reply.domain.ReplyLikeRelation;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationReplyLikeRelationService implements ReplyLikeRelationUseCase {
    private final CreateReplyLikeRelationUseCase createReplyLikeRelationUseCase;
    private final DeleteReplyLikeRelationService deleteReplyLikeRelationService;
    private final QueryReplyLikeRelationUseCase queryReplyLikeRelationUseCase;
    private final CheckReplyLikeRelationUseCase checkReplyLikeRelationUseCase;
    private final UpdateReplyUseCase updateReplyUseCase;
    private final QueryReplyUseCase queryReplyUseCase;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    @Transactional
    public void toggle(User user, Long id) {
        Reply reply = queryReplyUseCase.findById(id);
        cacheEvictUtil.evictCachingReply(reply.getPost());

        if(checkReplyLikeRelationUseCase.existsReplyLikeRelationByUserAndReply(user, reply)){
            ReplyLikeRelation relation = queryReplyLikeRelationUseCase.findReplyLikeRelationByUserAndReply(user, reply);
            deleteReplyLikeRelationService.delete(relation);
            updateReplyUseCase.decreaseLikeNum(reply);
        }else {
            createReplyLikeRelationUseCase.save(user, reply);
            updateReplyUseCase.increaseLikeNum(reply);
        }
    }
}
