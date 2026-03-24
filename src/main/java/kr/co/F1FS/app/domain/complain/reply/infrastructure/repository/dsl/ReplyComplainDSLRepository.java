package kr.co.F1FS.app.domain.complain.reply.infrastructure.repository.dsl;

import kr.co.F1FS.app.domain.complain.reply.domain.ReplyComplain;
import kr.co.F1FS.app.domain.complain.reply.presentation.dto.ResponseReplyComplainListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ReplyComplainDSLRepository {
    Optional<ReplyComplain> findById(Long id);
    Page<ResponseReplyComplainListDTO> findReplyComplainList(Pageable pageable);
    Page<ResponseReplyComplainListDTO> findAllByUser(Long userId, Pageable pageable);
}
