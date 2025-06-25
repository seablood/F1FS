package kr.co.F1FS.app.domain.complain.post.application.port.in;

import kr.co.F1FS.app.domain.complain.post.domain.PostComplain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostComplainUseCase {
    void save(PostComplain complain);
    Page<PostComplain> findAll(Pageable pageable);
}
