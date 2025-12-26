package kr.co.F1FS.app.domain.reply.application.port.in.replyComment;

import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.reply.domain.ReplyComment;
import kr.co.F1FS.app.global.presentation.dto.reply.ResponseReplyCommentDTO;

import java.util.List;
import java.util.Map;

public interface QueryReplyCommentUseCase {
    ReplyComment findById(Long id);
    Map<Long, List<ResponseReplyCommentDTO>> findByReply(List<Reply> replies);
}
