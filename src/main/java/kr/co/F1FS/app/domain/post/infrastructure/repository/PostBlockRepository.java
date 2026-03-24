package kr.co.F1FS.app.domain.post.infrastructure.repository;

import kr.co.F1FS.app.domain.post.domain.PostBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostBlockRepository extends JpaRepository<PostBlock, Long> {
}
