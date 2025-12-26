package kr.co.F1FS.app.domain.complain.post.application.port.in;

import kr.co.F1FS.app.domain.complain.post.domain.PostComplain;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.complain.ResponsePostComplainDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryPostComplainUseCase {
    PostComplain findById(Long id);
    Page<PostComplain> findAll(Pageable pageable);
    ResponsePostComplainDTO findByIdForDTO(Long id);
    Page<ResponsePostComplainDTO> findAllByUserForDTO(User user, Pageable pageable);
}
