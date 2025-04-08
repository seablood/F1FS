package kr.co.F1FS.app.repository;

import io.lettuce.core.dynamic.annotation.Param;
import kr.co.F1FS.app.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);
    Page<Post> findAllByTitleContainsIgnoreCase(String title, Pageable pageable);
    Page<Post> findAllByContentContainsIgnoreCase(String content, Pageable pageable);
    Page<Post> findAllByTitleContainsIgnoreCaseOrContentContainsIgnoreCase(String title, String content, Pageable pageable);
    @Query("SELECT p FROM Post p JOIN p.author u WHERE u.nickname LIKE %:nickname%")
    Page<Post> findByAuthorNicknameContainsIgnoreCase(@Param("nickname") String nickname, Pageable pageable);
}
