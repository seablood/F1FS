package kr.co.F1FS.app.domain.reply.application.service.replyComment;

import kr.co.F1FS.app.domain.notification.application.port.in.push.FCMLiveUseCase;
import kr.co.F1FS.app.domain.reply.application.port.in.replyComment.*;
import kr.co.F1FS.app.domain.reply.application.port.in.replying.QueryReplyUseCase;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.reply.domain.ReplyComment;
import kr.co.F1FS.app.domain.reply.presentation.dto.replyComment.CreateReplyCommentDTO;
import kr.co.F1FS.app.domain.reply.presentation.dto.replyComment.ModifyReplyCommentDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.reply.ResponseReplyCommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationReplyCommentService implements ReplyCommentUseCase {
    private final CreateReplyCommentUseCase createReplyCommentUseCase;
    private final UpdateReplyCommentUseCase updateReplyCommentUseCase;
    private final DeleteReplyCommentUseCase deleteReplyCommentUseCase;
    private final QueryReplyCommentUseCase queryReplyCommentUseCase;
    private final QueryReplyUseCase queryReplyUseCase;
    private final FCMLiveUseCase fcmLiveUseCase;

    @Override
    @Transactional
    public ReplyComment save(CreateReplyCommentDTO dto, User user, Long id) {
        Reply reply = queryReplyUseCase.findById(id);
        Post post = reply.getPost();
        ReplyComment replyComment = createReplyCommentUseCase.save(dto, user, reply);

        fcmLiveUseCase.sendPushAfterReplyComment(user, replyComment, post, post.getId());
        return replyComment;
    }

    @Override
    @Transactional
    public ResponseReplyCommentDTO modify(ModifyReplyCommentDTO dto, User user, Long replyCommentId) {
        ReplyComment replyComment = queryReplyCommentUseCase.findById(replyCommentId);

        return updateReplyCommentUseCase.modify(dto, user, replyComment);
    }

    @Override
    @Transactional
    public void delete(Long replyCommentId, User user) {
        ReplyComment replyComment = queryReplyCommentUseCase.findById(replyCommentId);

        deleteReplyCommentUseCase.delete(user, replyComment);
    }
}
