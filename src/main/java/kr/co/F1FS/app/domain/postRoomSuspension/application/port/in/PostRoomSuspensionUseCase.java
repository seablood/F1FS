package kr.co.F1FS.app.domain.postRoomSuspension.application.port.in;

import kr.co.F1FS.app.domain.postRoomSuspension.presentation.dto.CreatePostRoomSuspensionDTO;
import kr.co.F1FS.app.domain.postRoomSuspension.presentation.dto.ModifyPostRoomSuspensionDTO;
import kr.co.F1FS.app.domain.postRoomSuspension.presentation.dto.ResponsePostRoomSuspensionListDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.postRoomSuspension.ResponsePostRoomSuspensionDTO;
import org.springframework.data.domain.Page;

public interface PostRoomSuspensionUseCase {
    ResponsePostRoomSuspensionDTO save(CreatePostRoomSuspensionDTO dto, User user);
    ResponsePostRoomSuspensionDTO getPostRoomSuspensionById(Long id);
    Page<ResponsePostRoomSuspensionListDTO> getPostRoomSuspensionListByPostRoom(int page, int size, String condition, Long roomId);
    ResponsePostRoomSuspensionDTO modify(ModifyPostRoomSuspensionDTO dto, Long suspensionId, User user);
    void delete(Long id, User user);
}
