package kr.co.F1FS.app.domain.postRoom.application.port.in;

import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import kr.co.F1FS.app.domain.user.domain.User;

public interface CheckPostRoomUseCase {
    boolean certification(User user, PostRoom postRoom);
}
