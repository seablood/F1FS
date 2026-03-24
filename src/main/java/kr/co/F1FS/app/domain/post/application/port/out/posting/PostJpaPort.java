package kr.co.F1FS.app.domain.post.application.port.out.posting;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.post.presentation.dto.ResponsePostListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostJpaPort {
    Post save(Post post);
    Post saveAndFlush(Post post);
    Page<ResponsePostListDTO> findPostList(Pageable pageable);
    Page<ResponsePostListDTO> findAllByAuthor(Long authorId, Pageable pageable);
    Post findById(Long id);
    Post findPostById(Long id);
    Post findByIdWithJoin(Long id);
    void delete(Post post);
}
