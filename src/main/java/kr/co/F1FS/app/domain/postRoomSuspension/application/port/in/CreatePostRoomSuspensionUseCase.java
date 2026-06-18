package kr.co.F1FS.app.domain.postRoomSuspension.application.port.in;

import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import kr.co.F1FS.app.domain.postRoomSuspension.domain.PostRoomSuspension;
import kr.co.F1FS.app.domain.postRoomSuspension.presentation.dto.CreatePostRoomSuspensionDTO;
import kr.co.F1FS.app.domain.user.domain.User;

public interface CreatePostRoomSuspensionUseCase {
    PostRoomSuspension save(CreatePostRoomSuspensionDTO dto, User suspendUser, PostRoom postRoom);
}
