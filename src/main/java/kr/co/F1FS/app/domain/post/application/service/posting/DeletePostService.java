package kr.co.F1FS.app.domain.post.application.service.posting;

import kr.co.F1FS.app.domain.post.application.port.in.posting.DeletePostUseCase;
import kr.co.F1FS.app.domain.post.application.port.out.posting.PostJpaPort;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.global.util.exception.post.PostException;
import kr.co.F1FS.app.global.util.exception.post.PostExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletePostService implements DeletePostUseCase {
    private final PostJpaPort postJpaPort;
    private final PostDomainService postDomainService;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    public void delete(Post post, User user) {
        cacheEvictUtil.evictCachingPost(post);

        if(!postDomainService.certification(user, post)){
            throw new PostException(PostExceptionType.NOT_AUTHORITY_DELETE_POST);
        }

        postJpaPort.delete(post);
    }

    @Override
    public void delete(Post post) {
        cacheEvictUtil.evictCachingPost(post);

        postJpaPort.delete(post);
    }
}
