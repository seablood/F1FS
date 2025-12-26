package kr.co.F1FS.app.domain.reply.application.port.out.replyComment;

import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.reply.domain.ReplyComment;

import java.util.List;

public interface ReplyCommentJpaPort {
    ReplyComment save(ReplyComment replyComment);
    ReplyComment saveAndFlush(ReplyComment replyComment);
    ReplyComment findById(Long id);
    List<ReplyComment> findAllByReply(List<Reply> replies);
    void delete(ReplyComment replyComment);
}
