package kr.co.F1FS.app.domain.elastic.infrastructure.adapter;

import kr.co.F1FS.app.domain.elastic.application.port.out.UserSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.UserDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.UserSearchRepository;
import kr.co.F1FS.app.global.util.exception.user.UserException;
import kr.co.F1FS.app.global.util.exception.user.UserExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSearchRepoAdapter implements UserSearchRepoPort {
    private final UserSearchRepository userSearchRepository;

    @Override
    public void save(UserDocument userDocument) {
        userSearchRepository.save(userDocument);
    }

    @Override
    public UserDocument findById(Long id) {
        return userSearchRepository.findById(id)
                .orElseThrow(() -> new UserException(UserExceptionType.SEARCH_ERROR_USER));
    }

    @Override
    public void delete(UserDocument userDocument) {
        userSearchRepository.delete(userDocument);
    }
}
