package kr.co.F1FS.app.domain.postRoom.application.port.in.admin;

import kr.co.F1FS.app.domain.postRoom.presentation.dto.ResponsePostRoomListDTO;
import org.springframework.data.domain.Page;

public interface AdminPostRoomUseCase {
    Page<ResponsePostRoomListDTO> getPostRoomList(int page, int size, String condition);
    Page<ResponsePostRoomListDTO> getPostRoomListByUser(int page, int size, String condition, Long userId);
    void delete(Long roomId);
}
