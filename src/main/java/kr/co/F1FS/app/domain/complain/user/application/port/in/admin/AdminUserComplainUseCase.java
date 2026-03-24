package kr.co.F1FS.app.domain.complain.user.application.port.in.admin;

import kr.co.F1FS.app.domain.complain.user.presentation.dto.ResponseUserComplainListDTO;
import kr.co.F1FS.app.global.presentation.dto.complain.user.ResponseUserComplainDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminUserComplainUseCase {
    Page<ResponseUserComplainListDTO> getUserComplainAll(int page, int size, String condition);
    Page<ResponseUserComplainListDTO> getUserComplainListByToUser(int page, int size, String condition, String search);
    ResponseUserComplainDTO getUserComplainById(Long id);
    Pageable switchCondition(int page, int size, String condition);
}
