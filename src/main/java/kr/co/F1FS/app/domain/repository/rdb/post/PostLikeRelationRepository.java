package kr.co.F1FS.app.domain.repository.rdb.post;

import kr.co.F1FS.app.domain.model.rdb.Post;
import kr.co.F1FS.app.domain.model.rdb.PostLikeRelation;
import kr.co.F1FS.app.domain.model.rdb.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRelationRepository extends JpaRepository<PostLikeRelation, Long> {
    PostLikeRelation findPostLikeRelationByUserAndPost(User user, Post post);
    boolean existsPostLikeRelationByUserAndPost(User user, Post post);
}
