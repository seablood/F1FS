package kr.co.F1FS.app.domain.post.application.mapper.postLike;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.post.domain.PostLikeRelation;
import kr.co.F1FS.app.domain.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class PostLikeRelationMapper {
    public PostLikeRelation toPostLikeRelation(User user, Post post){
        return PostLikeRelation.builder()
                .user(user)
                .post(post)
                .build();
    }
}
