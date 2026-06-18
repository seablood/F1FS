package kr.co.F1FS.app.domain.postRoom.application.port.in;

import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import kr.co.F1FS.app.domain.postRoom.presentation.dto.ModifyPostRoomInfoDTO;
import kr.co.F1FS.app.domain.postRoom.presentation.dto.ModifyPostRoomPublicDTO;
import kr.co.F1FS.app.domain.user.domain.User;

public interface UpdatePostRoomUseCase {
    void increasePostCount(PostRoom postRoom);
    void decreasePostCount(PostRoom postRoom);
    void modifyInfo(ModifyPostRoomInfoDTO dto, PostRoom postRoom, User user);
    void modifyIsPublic(ModifyPostRoomPublicDTO dto, PostRoom postRoom, User user);
}
