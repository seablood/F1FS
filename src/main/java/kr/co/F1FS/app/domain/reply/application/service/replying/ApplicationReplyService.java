package kr.co.F1FS.app.domain.reply.application.service.replying;

import kr.co.F1FS.app.domain.notification.application.port.in.push.FCMLiveUseCase;
import kr.co.F1FS.app.domain.post.application.port.in.posting.QueryPostUseCase;
import kr.co.F1FS.app.domain.reply.application.port.in.replyComment.QueryReplyCommentUseCase;
import kr.co.F1FS.app.domain.reply.application.port.in.replying.*;
import kr.co.F1FS.app.global.presentation.dto.reply.ResponseReplyCommentDTO;
import kr.co.F1FS.app.domain.reply.presentation.dto.replying.CreateReplyDTO;
import kr.co.F1FS.app.domain.reply.presentation.dto.replying.ModifyReplyDTO;
import kr.co.F1FS.app.global.presentation.dto.reply.ResponseReplyDTO;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ApplicationReplyService implements ReplyUseCase {
    private final CreateReplyUseCase createReplyUseCase;
    private final UpdateReplyUseCase updateReplyUseCase;
    private final DeleteReplyUseCase deleteReplyUseCase;
    private final QueryReplyUseCase queryReplyUseCase;
    private final QueryPostUseCase queryPostUseCase;
    private final QueryReplyCommentUseCase queryReplyCommentUseCase;
    private final FCMLiveUseCase fcmLiveUseCase;

    @Override
    @Transactional
    public Reply save(CreateReplyDTO dto, User user, Long id){
        Post post = queryPostUseCase.findById(id);
        Reply reply = createReplyUseCase.save(dto, user, post);

        fcmLiveUseCase.sendPushAfterReply(user, reply, post, id);
        return reply;
    }

    @Override
    @Cacheable(value = "PostReplyList", key = "#id", cacheManager = "redisLongCacheManager")
    public List<ResponseReplyDTO> findByPost(Long id){
        Post post = queryPostUseCase.findById(id);
        List<Reply> replies = queryReplyUseCase.findAllByPost(post);
        Map<Long, List<ResponseReplyCommentDTO>> commentMap = queryReplyCommentUseCase.findByReply(replies);

        return queryReplyUseCase.findReplyList(post, replies, commentMap);
    }

    @Override
    @Transactional
    public ResponseReplyDTO modify(Long replyId, ModifyReplyDTO dto, User user){
        Reply reply = queryReplyUseCase.findById(replyId);

        return updateReplyUseCase.modify(reply, user, dto);
    }

    @Override
    @Transactional
    public void delete(Long replyId, User user){
        Reply reply = queryReplyUseCase.findById(replyId);

        deleteReplyUseCase.delete(reply, user);
    }
}
