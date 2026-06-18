package kr.co.F1FS.app.domain.postRoomSuspension.application.port.in;

import kr.co.F1FS.app.domain.postRoomSuspension.domain.PostRoomSuspension;
import kr.co.F1FS.app.domain.postRoomSuspension.presentation.dto.ModifyPostRoomSuspensionDTO;
import kr.co.F1FS.app.domain.user.domain.User;

public interface UpdatePostRoomSuspensionUseCase {
    void modify(ModifyPostRoomSuspensionDTO dto, PostRoomSuspension postRoomSuspension, User user);
}
