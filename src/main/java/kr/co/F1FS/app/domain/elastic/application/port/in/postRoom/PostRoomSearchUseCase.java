package kr.co.F1FS.app.domain.elastic.application.port.in.postRoom;

import kr.co.F1FS.app.global.presentation.dto.postRoom.ResponsePostRoomDocumentDTO;
import org.springframework.data.domain.Page;

public interface PostRoomSearchUseCase {
    Page<ResponsePostRoomDocumentDTO> getPostRoomList(int page, int size, String condition, String keyword);
    Page<ResponsePostRoomDocumentDTO> getPostRoomListByMasterUser(int page, int size, String condition, String keyword);
}
