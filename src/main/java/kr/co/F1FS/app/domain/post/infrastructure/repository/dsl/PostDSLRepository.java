package kr.co.F1FS.app.domain.post.infrastructure.repository.dsl;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.post.presentation.dto.ResponsePostListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PostDSLRepository {
    Optional<Post> findById(Long id);
    Page<ResponsePostListDTO> findPostList(Pageable pageable);
    Page<ResponsePostListDTO> findAllByAuthor(Long authorId, Pageable pageable);
}
