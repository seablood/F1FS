package kr.co.F1FS.app.domain.post.application.service.postLike;

import kr.co.F1FS.app.domain.post.application.port.in.postLike.DeletePostLikeRelationUseCase;
import kr.co.F1FS.app.domain.post.application.port.out.postLike.PostLikeRelationJpaPort;
import kr.co.F1FS.app.domain.post.domain.PostLikeRelation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletePostLikeRelationService implements DeletePostLikeRelationUseCase {
    private final PostLikeRelationJpaPort relationJpaPort;

    @Override
    public void delete(PostLikeRelation relation) {
        relationJpaPort.delete(relation);
    }
}
