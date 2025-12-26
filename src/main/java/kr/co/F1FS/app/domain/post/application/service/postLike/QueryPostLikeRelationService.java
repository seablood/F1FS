package kr.co.F1FS.app.domain.post.application.service.postLike;

import kr.co.F1FS.app.domain.post.application.port.in.postLike.QueryPostLikeRelationUseCase;
import kr.co.F1FS.app.domain.post.application.port.out.postLike.PostLikeRelationJpaPort;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.post.domain.PostLikeRelation;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryPostLikeRelationService implements QueryPostLikeRelationUseCase {
    private final PostLikeRelationJpaPort relationJpaPort;

    @Override
    public PostLikeRelation findPostLikeRelationByUserAndPost(User user, Post post) {
        return relationJpaPort.findPostLikeRelationByUserAndPost(user, post);
    }
}
