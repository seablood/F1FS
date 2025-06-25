package kr.co.F1FS.app.domain.post.infrastructure.repository;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.post.domain.PostLikeRelation;
import kr.co.F1FS.app.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRelationRepository extends JpaRepository<PostLikeRelation, Long> {
    PostLikeRelation findPostLikeRelationByUserAndPost(User user, Post post);
    boolean existsPostLikeRelationByUserAndPost(User user, Post post);
}
