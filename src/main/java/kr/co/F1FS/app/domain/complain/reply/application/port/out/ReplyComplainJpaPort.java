package kr.co.F1FS.app.domain.complain.reply.application.port.out;

import kr.co.F1FS.app.domain.complain.reply.domain.ReplyComplain;
import kr.co.F1FS.app.domain.complain.reply.presentation.dto.ResponseReplyComplainListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReplyComplainJpaPort {
    ReplyComplain save(ReplyComplain replyComplain);
    Page<ResponseReplyComplainListDTO> findReplyComplainList(Pageable pageable);
    ReplyComplain findByIdWithJoin(Long id);
    Page<ResponseReplyComplainListDTO> findAllByUser(Long userId, Pageable pageable);
    void delete(ReplyComplain replyComplain);
}
