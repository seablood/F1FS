package kr.co.F1FS.app.domain.post.application.service.postLike;

import kr.co.F1FS.app.domain.post.application.port.in.postLike.CreatePostLikeRelationUseCase;
import kr.co.F1FS.app.domain.post.application.port.out.postLike.PostLikeRelationJpaPort;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.post.domain.PostLikeRelation;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePostLikeRelationService implements CreatePostLikeRelationUseCase {
    private final PostLikeRelationJpaPort relationJpaPort;
    private final PostLikeRelationDomainService relationDomainService;

    @Override
    public void save(User user, Post post) {
        PostLikeRelation relation = relationDomainService.createEntity(user, post);

        relationJpaPort.save(relation);
    }
}
