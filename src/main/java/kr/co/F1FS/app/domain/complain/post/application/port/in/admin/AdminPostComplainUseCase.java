package kr.co.F1FS.app.domain.complain.post.application.port.in.admin;

import kr.co.F1FS.app.domain.complain.post.presentation.dto.ResponsePostComplainListDTO;
import kr.co.F1FS.app.global.presentation.dto.complain.post.ResponsePostComplainDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminPostComplainUseCase {
    Page<ResponsePostComplainListDTO> getPostComplainAll(int page, int size, String condition);
    ResponsePostComplainDTO getPostComplainById(Long id);
    Pageable switchCondition(int page, int size, String condition);
}
