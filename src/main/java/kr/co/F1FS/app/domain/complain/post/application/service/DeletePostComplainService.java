package kr.co.F1FS.app.domain.complain.post.application.service;

import kr.co.F1FS.app.domain.complain.post.application.port.in.DeletePostComplainUseCase;
import kr.co.F1FS.app.domain.complain.post.application.port.out.PostComplainJpaPort;
import kr.co.F1FS.app.domain.complain.post.domain.PostComplain;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletePostComplainService implements DeletePostComplainUseCase {
    private final PostComplainJpaPort postComplainJpaPort;
    private final PostComplainDomainService postComplainDomainService;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    public void delete(PostComplain postComplain, User user) {
        cacheEvictUtil.evictCachingPostComplain(postComplain);

        if(postComplainDomainService.certification(postComplain, user)){
            postComplainJpaPort.delete(postComplain);
        }
    }
}
