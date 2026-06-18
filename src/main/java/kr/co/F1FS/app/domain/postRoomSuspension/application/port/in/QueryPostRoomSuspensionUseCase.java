package kr.co.F1FS.app.domain.postRoomSuspension.application.port.in;

import kr.co.F1FS.app.domain.postRoomSuspension.domain.PostRoomSuspension;
import kr.co.F1FS.app.domain.postRoomSuspension.presentation.dto.ResponsePostRoomSuspensionListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QueryPostRoomSuspensionUseCase {
    PostRoomSuspension findByIdWithJoin(Long id);
    Page<ResponsePostRoomSuspensionListDTO> findPostRoomSuspensionListByPostRoom(Long roomId, Pageable pageable);
}
