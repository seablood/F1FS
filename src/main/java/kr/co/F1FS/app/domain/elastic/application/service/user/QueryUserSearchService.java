package kr.co.F1FS.app.domain.elastic.application.service.user;

import kr.co.F1FS.app.domain.elastic.application.port.in.user.QueryUserSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.UserSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.UserDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.UserSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryUserSearchService implements QueryUserSearchUseCase {
    private final UserSearchRepository userSearchRepository;
    private final UserSearchRepoPort userSearchRepoPort;

    @Override
    public UserDocument findById(Long id) {
        return userSearchRepoPort.findById(id);
    }
}
