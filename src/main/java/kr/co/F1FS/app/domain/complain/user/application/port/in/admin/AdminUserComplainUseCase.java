package kr.co.F1FS.app.domain.complain.user.application.port.in.admin;

import kr.co.F1FS.app.global.presentation.dto.complain.user.ResponseUserComplainDTO;
import kr.co.F1FS.app.global.presentation.dto.complain.user.SimpleResponseUserComplainDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminUserComplainUseCase {
    Page<SimpleResponseUserComplainDTO> getUserComplainAll(int page, int size, String condition);
    Page<SimpleResponseUserComplainDTO> getUserComplainListByToUser(int page, int size, String condition, String search);
    ResponseUserComplainDTO getUserComplainById(Long id);
    Pageable switchCondition(int page, int size, String condition);
}
