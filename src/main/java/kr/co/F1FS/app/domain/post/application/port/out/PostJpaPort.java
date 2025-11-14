package kr.co.F1FS.app.domain.post.application.port.out;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.post.ResponseSimplePostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostJpaPort {
    Post save(Post post);
    Post saveAndFlush(Post post);
    Page<ResponseSimplePostDTO> findAll(Pageable pageable);
    Page<Post> findAllByAuthor(User user, Pageable pageable);
    Post findById(Long id);
    void delete(Post post);
}
