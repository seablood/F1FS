package kr.co.F1FS.app.domain.complain.reply.application.port.in.admin;

import kr.co.F1FS.app.global.presentation.dto.complain.reply.ResponseReplyComplainDTO;
import kr.co.F1FS.app.global.presentation.dto.complain.reply.SimpleResponseReplyComplainDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminReplyComplainUseCase {
    Page<SimpleResponseReplyComplainDTO> getReplyComplainAll(int page, int size, String condition);
    ResponseReplyComplainDTO getReplyComplainById(Long id);
    Pageable switchCondition(int page, int size, String condition);
}
