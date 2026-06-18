package kr.co.F1FS.app.domain.elastic.infrastructure.adapter;

import kr.co.F1FS.app.domain.elastic.application.port.out.PostRoomSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.PostRoomDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.PostRoomSearchRepository;
import kr.co.F1FS.app.global.util.exception.postRoom.PostRoomException;
import kr.co.F1FS.app.global.util.exception.postRoom.PostRoomExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostRoomSearchRepoAdapter implements PostRoomSearchRepoPort {
    private final PostRoomSearchRepository postRoomSearchRepository;

    @Override
    public void save(PostRoomDocument document) {
        postRoomSearchRepository.save(document);
    }

    @Override
    public PostRoomDocument findById(Long id) {
        return postRoomSearchRepository.findById(id)
                .orElseThrow(() -> new PostRoomException(PostRoomExceptionType.POST_ROOM_NOT_FOUND));
    }

    @Override
    public void delete(PostRoomDocument document) {
        postRoomSearchRepository.delete(document);
    }
}
