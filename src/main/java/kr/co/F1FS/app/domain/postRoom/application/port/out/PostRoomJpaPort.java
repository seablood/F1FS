package kr.co.F1FS.app.domain.postRoom.application.port.out;

import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import kr.co.F1FS.app.domain.postRoom.presentation.dto.ResponsePostRoomListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRoomJpaPort {
    PostRoom save(PostRoom postRoom);
    PostRoom saveAndFlush(PostRoom postRoom);
    PostRoom findById(Long id);
    PostRoom findByIdWithJoin(Long id);
    Page<ResponsePostRoomListDTO> findPostRoomList(Pageable pageable);
    Page<ResponsePostRoomListDTO> findAllByUser(Long userId, Pageable pageable);
    void delete(PostRoom postRoom);
}
