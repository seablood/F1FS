package kr.co.F1FS.app.domain.complain.user.application.service;

import kr.co.F1FS.app.domain.complain.user.application.port.in.DeleteUserComplainUseCase;
import kr.co.F1FS.app.domain.complain.user.application.port.out.UserComplainJpaPort;
import kr.co.F1FS.app.domain.complain.user.domain.UserComplain;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.AuthorCertification;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.global.util.exception.user.UserException;
import kr.co.F1FS.app.global.util.exception.user.UserExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserComplainService implements DeleteUserComplainUseCase {
    private final UserComplainJpaPort userComplainJpaPort;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    public void delete(UserComplain userComplain, User user) {
        if(AuthorCertification.certification(user.getUsername(), userComplain.getFromUser().getUsername())){
            cacheEvictUtil.evictCachingUserComplain(userComplain);
            userComplainJpaPort.delete(userComplain);
        }else {
            throw new UserException(UserExceptionType.NOT_AUTHORITY_DELETE_USER_COMPLAIN);
        }
    }

    @Override
    public void delete(UserComplain userComplain) {
        cacheEvictUtil.evictCachingUserComplain(userComplain);
        userComplainJpaPort.delete(userComplain);
    }
}
