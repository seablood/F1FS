package kr.co.F1FS.app.domain.postRoomSuspension.application.port.out;

import kr.co.F1FS.app.domain.postRoomSuspension.domain.PostRoomSuspension;
import kr.co.F1FS.app.domain.postRoomSuspension.presentation.dto.ResponsePostRoomSuspensionListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRoomSuspensionJpaPort {
    PostRoomSuspension save(PostRoomSuspension postRoomSuspension);
    PostRoomSuspension saveAndFlush(PostRoomSuspension postRoomSuspension);
    PostRoomSuspension findByIdWithJoin(Long id);
    Page<ResponsePostRoomSuspensionListDTO> findPostRoomSuspensionListByPostRoom(Long roomId, Pageable pageable);
    void delete(PostRoomSuspension postRoomSuspension);
}
