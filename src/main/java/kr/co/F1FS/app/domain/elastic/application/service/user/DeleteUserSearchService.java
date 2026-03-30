package kr.co.F1FS.app.domain.elastic.application.service.user;

import kr.co.F1FS.app.domain.elastic.application.port.in.user.DeleteUserSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.UserSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.UserDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.UserSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserSearchService implements DeleteUserSearchUseCase {
    private final UserSearchRepository userSearchRepository;
    private final UserSearchRepoPort userSearchRepoPort;

    @Override
    public void delete(UserDocument userDocument) {
        userSearchRepoPort.delete(userDocument);
    }
}
