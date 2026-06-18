package kr.co.F1FS.app.domain.elastic.application.service.postRoom;

import kr.co.F1FS.app.domain.elastic.application.port.in.postRoom.QueryPostRoomSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.PostRoomSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.PostRoomDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.PostRoomSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryPostRoomSearchService implements QueryPostRoomSearchUseCase {
    private final PostRoomSearchRepository postRoomSearchRepository;
    private final PostRoomSearchRepoPort postRoomSearchRepoPort;

    @Override
    public PostRoomDocument findById(Long id) {
        return postRoomSearchRepoPort.findById(id);
    }
}
