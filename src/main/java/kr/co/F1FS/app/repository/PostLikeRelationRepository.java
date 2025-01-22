package kr.co.F1FS.app.repository;

import kr.co.F1FS.app.model.Post;
import kr.co.F1FS.app.model.PostLikeRelation;
import kr.co.F1FS.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRelationRepository extends JpaRepository<PostLikeRelation, Long> {
    PostLikeRelation findPostLikeRelationByUserAndPost(User user, Post post);
    boolean existsPostLikeRelationByUserAndPost(User user, Post post);
}
