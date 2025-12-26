package kr.co.F1FS.app.domain.post.application.service.postLike;

import kr.co.F1FS.app.domain.post.application.port.in.postLike.CheckPostLikeRelationUseCase;
import kr.co.F1FS.app.domain.post.application.port.out.postLike.PostLikeRelationJpaPort;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckPostLikeRelationService implements CheckPostLikeRelationUseCase {
    private final PostLikeRelationJpaPort relationJpaPort;

    @Override
    public boolean existsPostLikeRelationByUserAndPost(User user, Post post) {
        return relationJpaPort.existsPostLikeRelationByUserAndPost(user, post);
    }
}
