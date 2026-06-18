package kr.co.F1FS.app.domain.elastic.application.service.postRoom;

import kr.co.F1FS.app.domain.elastic.application.port.in.postRoom.UpdatePostRoomSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.PostRoomSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.PostRoomDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.PostRoomSearchRepository;
import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdatePostRoomSearchService implements UpdatePostRoomSearchUseCase {
    private final PostRoomSearchRepository postRoomSearchRepository;
    private final PostRoomSearchRepoPort postRoomSearchRepoPort;
    private final PostRoomDocumentService postRoomDocumentService;

    @Override
    public void modifyInfo(PostRoom postRoom, PostRoomDocument document) {
        postRoomDocumentService.modifyInfo(postRoom, document);

        postRoomSearchRepoPort.save(document);
    }

    @Override
    public void modifyIsPublic(PostRoom postRoom, PostRoomDocument document) {
        postRoomDocumentService.modifyIsPublic(postRoom, document);

        postRoomSearchRepoPort.save(document);
    }
}
