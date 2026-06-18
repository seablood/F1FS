package kr.co.F1FS.app.domain.postRoom.application.port.in;

import jakarta.servlet.http.HttpServletResponse;
import kr.co.F1FS.app.domain.postRoom.presentation.dto.*;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.postRoom.ResponsePostRoomDTO;
import org.springframework.data.domain.Page;

public interface PostRoomUseCase {
    ResponsePostRoomDTO save(CreatePostRoomDTO dto, User user, Long formId);
    Page<ResponsePostRoomListDTO> getPostRoomList(int page, int size, String condition);
    Page<ResponsePostRoomListDTO> getPostRoomListByUser(int page, int size, String condition, User user);
    ResponsePostRoomDTO modifyInfo(ModifyPostRoomInfoDTO dto, Long postRoomId, User user);
    ResponsePostRoomDTO modifyIsPublic(ModifyPostRoomPublicDTO dto, Long postRoomId, User user);
    void verifyPrivatePostRoom(VerifyPostRoomDTO dto, Long postRoomId, User user, HttpServletResponse response);
    void delete(Long roomId);
}
