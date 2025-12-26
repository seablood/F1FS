package kr.co.F1FS.app.domain.complain.post.application.port.out;

import kr.co.F1FS.app.domain.complain.post.domain.PostComplain;
import kr.co.F1FS.app.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostComplainJpaPort {
    PostComplain save(PostComplain postComplain);
    Page<PostComplain> findAll(Pageable pageable);
    Page<PostComplain> findAllByUser(User fromUser, Pageable pageable);
    PostComplain findById(Long id);
    void delete(PostComplain postComplain);
}
