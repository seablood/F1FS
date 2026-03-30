package kr.co.F1FS.app.domain.elastic.application.service.user;

import kr.co.F1FS.app.domain.elastic.application.port.in.user.UpdateUserSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.UserSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.UserDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.UserSearchRepository;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUserSearchService implements UpdateUserSearchUseCase {
    private final UserSearchRepository userSearchRepository;
    private final UserSearchRepoPort userSearchRepoPort;
    private final UserSearchDocumentService userSearchDocumentService;

    @Override
    public void modify(UserDocument userDocument, User user) {
        userSearchDocumentService.modify(userDocument, user);

        userSearchRepoPort.save(userDocument);
    }

    @Override
    public void increaseFollowerNum(UserDocument userDocument) {
        userSearchDocumentService.increaseFollowerNum(userDocument);

        userSearchRepoPort.save(userDocument);
    }

    @Override
    public void decreaseFollowerNum(UserDocument userDocument) {
        userSearchDocumentService.decreaseFollowerNum(userDocument);

        userSearchRepoPort.save(userDocument);
    }
}
