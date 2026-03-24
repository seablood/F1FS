package kr.co.F1FS.app.domain.complain.post.infrastructure.repository.dsl;

import kr.co.F1FS.app.domain.complain.post.domain.PostComplain;
import kr.co.F1FS.app.domain.complain.post.presentation.dto.ResponsePostComplainListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PostComplainDSLRepository {
    Optional<PostComplain> findById(Long id);
    Page<ResponsePostComplainListDTO> findPostComplainList(Pageable pageable);
    Page<ResponsePostComplainListDTO> findAllByUser(Long userId, Pageable pageable);
}
