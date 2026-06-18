package kr.co.F1FS.app.domain.postRoom.infrastructure.adapter;

import kr.co.F1FS.app.domain.postRoom.application.port.out.PostRoomJpaPort;
import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import kr.co.F1FS.app.domain.postRoom.infrastructure.repository.PostRoomRepository;
import kr.co.F1FS.app.domain.postRoom.infrastructure.repository.dsl.PostRoomDSLRepository;
import kr.co.F1FS.app.domain.postRoom.presentation.dto.ResponsePostRoomListDTO;
import kr.co.F1FS.app.global.util.exception.postRoom.PostRoomException;
import kr.co.F1FS.app.global.util.exception.postRoom.PostRoomExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostRoomJpaAdapter implements PostRoomJpaPort {
    private final PostRoomRepository postRoomRepository;
    private final PostRoomDSLRepository postRoomDSLRepository;

    @Override
    public PostRoom save(PostRoom postRoom) {
        return postRoomRepository.save(postRoom);
    }

    @Override
    public PostRoom saveAndFlush(PostRoom postRoom) {
        return postRoomRepository.saveAndFlush(postRoom);
    }

    @Override
    public PostRoom findById(Long id) {
        return postRoomRepository.findById(id)
                .orElseThrow(() -> new PostRoomException(PostRoomExceptionType.POST_ROOM_NOT_FOUND));
    }

    @Override
    public PostRoom findByIdWithJoin(Long id) {
        return postRoomDSLRepository.findById(id)
                .orElseThrow(() -> new PostRoomException(PostRoomExceptionType.POST_ROOM_NOT_FOUND));
    }

    @Override
    public Page<ResponsePostRoomListDTO> findPostRoomList(Pageable pageable) {
        return postRoomDSLRepository.findPostRoomList(pageable);
    }

    @Override
    public Page<ResponsePostRoomListDTO> findAllByUser(Long userId, Pageable pageable) {
        return postRoomDSLRepository.findAllByUser(userId, pageable);
    }

    @Override
    public void delete(PostRoom postRoom) {
        postRoomRepository.delete(postRoom);
    }
}
