package kr.co.F1FS.app.domain.complain.post.infrastructure.repository;

import kr.co.F1FS.app.domain.complain.post.domain.PostComplain;
import kr.co.F1FS.app.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostComplainRepository extends JpaRepository<PostComplain, Long> {
    Page<PostComplain> findAll(Pageable pageable);
    Page<PostComplain> findAllByFromUser(User fromUser, Pageable pageable);
}
