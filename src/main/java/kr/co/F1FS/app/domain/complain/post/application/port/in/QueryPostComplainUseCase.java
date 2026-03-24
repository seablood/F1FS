package kr.co.F1FS.app.domain.complain.post.application.port.in;

import kr.co.F1FS.app.domain.complain.post.domain.PostComplain;
import kr.co.F1FS.app.domain.complain.post.presentation.dto.ResponsePostComplainListDTO;
import kr.co.F1FS.app.global.presentation.dto.complain.post.ResponsePostComplainDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryPostComplainUseCase {
    PostComplain findByIdWithJoin(Long id);
    Page<ResponsePostComplainListDTO> findPostComplainListForDTO(Pageable pageable);
    ResponsePostComplainDTO findByIdForDTO(Long id);
    Page<ResponsePostComplainListDTO> findAllByUserForDTO(Long userId, Pageable pageable);
}
