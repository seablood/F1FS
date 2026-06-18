package kr.co.F1FS.app.domain.postRoom.infrastructure.repository.dsl;

import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import kr.co.F1FS.app.domain.postRoom.presentation.dto.ResponsePostRoomListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PostRoomDSLRepository {
    Optional<PostRoom> findById(Long id);
    Page<ResponsePostRoomListDTO> findPostRoomList(Pageable pageable);
    Page<ResponsePostRoomListDTO> findAllByUser(Long userId, Pageable pageable);
}
