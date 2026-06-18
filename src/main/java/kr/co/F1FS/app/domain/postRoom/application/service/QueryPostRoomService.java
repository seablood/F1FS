package kr.co.F1FS.app.domain.postRoom.application.service;

import kr.co.F1FS.app.domain.postRoom.application.port.in.QueryPostRoomUseCase;
import kr.co.F1FS.app.domain.postRoom.application.port.out.PostRoomJpaPort;
import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import kr.co.F1FS.app.domain.postRoom.presentation.dto.ResponsePostRoomListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryPostRoomService implements QueryPostRoomUseCase {
    private final PostRoomJpaPort postRoomJpaPort;

    @Override
    public PostRoom findById(Long id) {
        return postRoomJpaPort.findById(id);
    }

    @Override
    public PostRoom findByIdWithJoin(Long id) {
        return postRoomJpaPort.findByIdWithJoin(id);
    }

    @Override
    public Page<ResponsePostRoomListDTO> findPostRoomListForDTO(Pageable pageable) {
        return postRoomJpaPort.findPostRoomList(pageable);
    }

    @Override
    public Page<ResponsePostRoomListDTO> findAllByUserForDTO(Long userId, Pageable pageable) {
        return postRoomJpaPort.findAllByUser(userId, pageable);
    }
}
