package kr.co.F1FS.app.domain.reply.application.port.in.admin;

import kr.co.F1FS.app.global.presentation.dto.reply.ResponseReplyByUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminReplyUseCase {
    Page<ResponseReplyByUserDTO> findReplyByUser(int page, int size, String condition, Long id);
    Pageable switchCondition(int page, int size, String condition);
}
