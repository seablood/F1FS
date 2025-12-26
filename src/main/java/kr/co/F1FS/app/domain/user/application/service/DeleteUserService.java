package kr.co.F1FS.app.domain.user.application.service;

import kr.co.F1FS.app.domain.user.application.port.in.DeleteUserUseCase;
import kr.co.F1FS.app.domain.user.application.port.out.UserJpaPort;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserService implements DeleteUserUseCase {
    private final UserJpaPort userJpaPort;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    public void delete(User user) {
        cacheEvictUtil.evictCachingUser(user);
        userJpaPort.delete(user);
    }
}
