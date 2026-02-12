package kr.co.F1FS.app.domain.complain.post.application.port.in;

import kr.co.F1FS.app.domain.complain.post.domain.PostComplain;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.complain.post.ResponsePostComplainDTO;
import kr.co.F1FS.app.global.presentation.dto.complain.post.SimpleResponsePostComplainDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryPostComplainUseCase {
    PostComplain findById(Long id);
    Page<SimpleResponsePostComplainDTO> findAllForDTO(Pageable pageable);
    ResponsePostComplainDTO findByIdForDTO(Long id);
    Page<SimpleResponsePostComplainDTO> findAllByUserForDTO(User user, Pageable pageable);
}
