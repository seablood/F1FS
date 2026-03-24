package kr.co.F1FS.app.domain.post.application.port.in.posting;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.post.presentation.dto.ResponsePostListDTO;
import kr.co.F1FS.app.global.presentation.dto.post.ResponsePostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryPostUseCase {
    Post findById(Long id);
    Post findPostById(Long id);
    Post findByIdWithJoin(Long id);
    ResponsePostDTO findByIdForDTO(Long id);
    Page<ResponsePostListDTO> findPostListForDTO(Pageable pageable);
    Page<ResponsePostListDTO> findAllByAuthorForDTO(Long authorId, Pageable pageable);
}
