package kr.co.F1FS.app.domain.reply.infrastructure.adapter.replyComment;

import kr.co.F1FS.app.domain.reply.application.port.out.replyComment.ReplyCommentJpaPort;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.reply.domain.ReplyComment;
import kr.co.F1FS.app.domain.reply.infrastructure.repository.ReplyCommentRepository;
import kr.co.F1FS.app.global.util.exception.reply.ReplyCommentException;
import kr.co.F1FS.app.global.util.exception.reply.ReplyCommentExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ReplyCommentJpaAdapter implements ReplyCommentJpaPort {
    private final ReplyCommentRepository replyCommentRepository;

    @Override
    public ReplyComment save(ReplyComment replyComment) {
        return replyCommentRepository.save(replyComment);
    }

    @Override
    public ReplyComment saveAndFlush(ReplyComment replyComment) {
        return replyCommentRepository.saveAndFlush(replyComment);
    }

    @Override
    public ReplyComment findById(Long id) {
        return replyCommentRepository.findById(id)
                .orElseThrow(() -> new ReplyCommentException(ReplyCommentExceptionType.REPLY_COMMENT_NOT_FOUND));
    }

    @Override
    public List<ReplyComment> findAllByReply(List<Reply> replies) {
        return replyCommentRepository.findAllByReplyIn(replies);
    }

    @Override
    public void delete(ReplyComment replyComment) {
        replyCommentRepository.delete(replyComment);
    }
}
