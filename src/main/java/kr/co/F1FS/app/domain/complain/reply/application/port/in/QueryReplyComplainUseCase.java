package kr.co.F1FS.app.domain.complain.reply.application.port.in;

import kr.co.F1FS.app.domain.complain.reply.domain.ReplyComplain;
import kr.co.F1FS.app.domain.complain.reply.presentation.dto.ResponseReplyComplainListDTO;
import kr.co.F1FS.app.global.presentation.dto.complain.reply.ResponseReplyComplainDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryReplyComplainUseCase {
    Page<ResponseReplyComplainListDTO> findReplyComplainListForDTO(Pageable pageable);
    ReplyComplain findByIdWithJoin(Long id);
    ResponseReplyComplainDTO findByIdForDTO(Long id);
    Page<ResponseReplyComplainListDTO> findAllByUserForDTO(Long userId, Pageable pageable);
}
