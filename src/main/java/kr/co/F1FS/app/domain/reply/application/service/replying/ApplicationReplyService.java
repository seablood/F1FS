package kr.co.F1FS.app.domain.reply.application.service.replying;

import kr.co.F1FS.app.domain.notification.application.port.in.push.FCMLiveUseCase;
import kr.co.F1FS.app.domain.post.application.port.in.posting.QueryPostUseCase;
import kr.co.F1FS.app.domain.reply.application.port.in.replying.*;
import kr.co.F1FS.app.domain.reply.presentation.dto.replying.ResponseReplyListDTO;
import kr.co.F1FS.app.domain.reply.presentation.dto.replying.CreateReplyDTO;
import kr.co.F1FS.app.domain.reply.presentation.dto.replying.ModifyReplyDTO;
import kr.co.F1FS.app.global.presentation.dto.reply.ResponseReplyDTO;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationReplyService implements ReplyUseCase {
    private final CreateReplyUseCase createReplyUseCase;
    private final UpdateReplyUseCase updateReplyUseCase;
    private final DeleteReplyUseCase deleteReplyUseCase;
    private final QueryReplyUseCase queryReplyUseCase;
    private final QueryPostUseCase queryPostUseCase;
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
    @Transactional(readOnly = true)
    public List<ResponseReplyListDTO> getReplyListByPost(Long id){
        return queryReplyUseCase.findAllByPostForDTO(id);
    }

    @Override
    @Transactional
    public ResponseReplyDTO modify(Long replyId, ModifyReplyDTO dto, User user){
        Reply reply = queryReplyUseCase.findByIdForPostWithJoin(replyId);

        return updateReplyUseCase.modify(reply, user, dto);
    }

    @Override
    @Transactional
    public void delete(Long replyId, User user){
        Reply reply = queryReplyUseCase.findByIdForPostWithJoin(replyId);

        deleteReplyUseCase.delete(reply, user);
    }
}
