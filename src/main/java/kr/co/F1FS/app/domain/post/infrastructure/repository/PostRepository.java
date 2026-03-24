package kr.co.F1FS.app.domain.post.infrastructure.repository;

import kr.co.F1FS.app.domain.post.domain.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @EntityGraph(attributePaths = {"author"})
    Optional<Post> findById(Long id);
    Optional<Post> findPostById(Long id);
}
