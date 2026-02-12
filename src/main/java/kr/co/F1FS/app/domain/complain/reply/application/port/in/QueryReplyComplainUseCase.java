package kr.co.F1FS.app.domain.complain.reply.application.port.in;

import kr.co.F1FS.app.domain.complain.reply.domain.ReplyComplain;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.complain.reply.ResponseReplyComplainDTO;
import kr.co.F1FS.app.global.presentation.dto.complain.reply.SimpleResponseReplyComplainDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryReplyComplainUseCase {
    Page<SimpleResponseReplyComplainDTO> findAllForDTO(Pageable pageable);
    ReplyComplain findById(Long id);
    ResponseReplyComplainDTO findByIdForDTO(Long id);
    Page<SimpleResponseReplyComplainDTO> findAllByFromUserForDTO(User user, Pageable pageable);
}
