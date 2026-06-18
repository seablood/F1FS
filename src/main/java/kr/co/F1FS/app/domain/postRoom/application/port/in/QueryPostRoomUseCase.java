package kr.co.F1FS.app.domain.postRoom.application.port.in;

import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import kr.co.F1FS.app.domain.postRoom.presentation.dto.ResponsePostRoomListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryPostRoomUseCase {
    PostRoom findById(Long id);
    PostRoom findByIdWithJoin(Long id);
    Page<ResponsePostRoomListDTO> findPostRoomListForDTO(Pageable pageable);
    Page<ResponsePostRoomListDTO> findAllByUserForDTO(Long userId, Pageable pageable);
}
