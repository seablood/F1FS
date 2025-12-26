package kr.co.F1FS.app.domain.reply.application.port.in.replying;

import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.reply.presentation.dto.replying.CreateReplyDTO;
import kr.co.F1FS.app.domain.reply.presentation.dto.replying.ModifyReplyDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.reply.ResponseReplyDTO;

import java.util.List;

public interface ReplyUseCase {
    Reply save(CreateReplyDTO dto, User user, Long id);
    List<ResponseReplyDTO> findByPost(Long id);
    ResponseReplyDTO modify(Long replyId, ModifyReplyDTO dto, User user);
    void delete(Long replyId, User user);
}
