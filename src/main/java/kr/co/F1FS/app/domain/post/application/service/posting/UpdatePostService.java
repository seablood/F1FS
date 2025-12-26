package kr.co.F1FS.app.domain.post.application.service.posting;

import kr.co.F1FS.app.domain.post.application.port.in.posting.UpdatePostUseCase;
import kr.co.F1FS.app.domain.post.application.port.out.posting.PostJpaPort;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.post.presentation.dto.ModifyPostDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.global.util.exception.post.PostException;
import kr.co.F1FS.app.global.util.exception.post.PostExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdatePostService implements UpdatePostUseCase {
    private final PostJpaPort postJpaPort;
    private final PostDomainService postDomainService;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    public void modify(ModifyPostDTO dto, Post post, User user) {
        cacheEvictUtil.evictCachingPost(post);

        if(!postDomainService.certification(user, post)){
            throw new PostException(PostExceptionType.NOT_AUTHORITY_UPDATE_POST);
        }

        postDomainService.modify(dto, post);
        postJpaPort.saveAndFlush(post);
    }

    @Override
    public void increaseLike(Post post) {
        postDomainService.increaseLike(post);

        postJpaPort.saveAndFlush(post);
    }

    @Override
    public void decreaseLike(Post post) {
        postDomainService.decreaseLike(post);

        postJpaPort.saveAndFlush(post);
    }
}
