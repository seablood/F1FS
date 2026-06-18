package kr.co.F1FS.app.domain.postRoomSuspension.application.port.in.admin;

import kr.co.F1FS.app.domain.postRoomSuspension.presentation.dto.ResponsePostRoomSuspensionListDTO;
import kr.co.F1FS.app.global.presentation.dto.postRoomSuspension.ResponsePostRoomSuspensionDTO;
import org.springframework.data.domain.Page;

public interface AdminPostRoomSuspensionUseCase {
    Page<ResponsePostRoomSuspensionListDTO> getPostRoomSuspensionListByPostRoom(int page, int size, String condition, Long roomId);
    ResponsePostRoomSuspensionDTO getPostRoomSuspensionById(Long id);
}
