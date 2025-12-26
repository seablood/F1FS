package kr.co.F1FS.app.domain.post.infrastructure.adapter.postLike;

import kr.co.F1FS.app.domain.post.application.port.out.postLike.PostLikeRelationJpaPort;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.post.domain.PostLikeRelation;
import kr.co.F1FS.app.domain.post.infrastructure.repository.PostLikeRelationRepository;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostLikeRelationJpaAdapter implements PostLikeRelationJpaPort {
    private final PostLikeRelationRepository relationRepository;

    @Override
    public PostLikeRelation save(PostLikeRelation relation) {
        return relationRepository.save(relation);
    }

    @Override
    public PostLikeRelation findPostLikeRelationByUserAndPost(User user, Post post) {
        return relationRepository.findPostLikeRelationByUserAndPost(user, post);
    }

    @Override
    public boolean existsPostLikeRelationByUserAndPost(User user, Post post) {
        return relationRepository.existsPostLikeRelationByUserAndPost(user, post);
    }

    @Override
    public void delete(PostLikeRelation relation) {
        relationRepository.delete(relation);
    }
}
