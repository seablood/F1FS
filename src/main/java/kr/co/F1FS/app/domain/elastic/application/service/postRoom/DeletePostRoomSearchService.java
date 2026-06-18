package kr.co.F1FS.app.domain.elastic.application.service.postRoom;

import kr.co.F1FS.app.domain.elastic.application.port.in.postRoom.DeletePostRoomSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.PostRoomSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.PostRoomDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.PostRoomSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeletePostRoomSearchService implements DeletePostRoomSearchUseCase {
    private final PostRoomSearchRepository postRoomSearchRepository;
    private final PostRoomSearchRepoPort postRoomSearchRepoPort;

    @Override
    public void delete(PostRoomDocument document) {
        postRoomSearchRepoPort.delete(document);
    }
}
