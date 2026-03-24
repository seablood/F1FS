package kr.co.F1FS.app.domain.post.application.service.postLike;

import kr.co.F1FS.app.domain.post.application.mapper.postLike.PostLikeRelationMapper;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.post.domain.PostLikeRelation;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostLikeRelationDomainService {
    private final PostLikeRelationMapper relationMapper;

    public PostLikeRelation createEntity(User user, Post post){
        return relationMapper.toPostLikeRelation(user, post);
    }
}
