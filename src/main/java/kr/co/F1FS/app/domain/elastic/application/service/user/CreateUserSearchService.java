package kr.co.F1FS.app.domain.elastic.application.service.user;

import kr.co.F1FS.app.domain.elastic.application.port.in.user.CreateUserSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.UserSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.UserDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.UserSearchRepository;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateUserSearchService implements CreateUserSearchUseCase {
    private final UserSearchRepository userSearchRepository;
    private final UserSearchRepoPort userSearchRepoPort;
    private final UserSearchDocumentService userSearchDocumentService;

    @Override
    public void save(User user) {
        UserDocument userDocument = userSearchDocumentService.createDocument(user);

        userSearchRepoPort.save(userDocument);
    }
}
