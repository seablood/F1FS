package kr.co.F1FS.app.domain.complain.post.infrastructure.repository;

import kr.co.F1FS.app.domain.complain.post.domain.PostComplain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostComplainRepository extends JpaRepository<PostComplain, Long> {
}
