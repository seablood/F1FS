package kr.co.F1FS.app.domain.admin.post.application.port.out;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AdminPostPort {
    Page<Post> findAllByAuthor(User user, Pageable pageable);
    Post findByIdNotDTO(Long id);
    void delete(Post post);
}
