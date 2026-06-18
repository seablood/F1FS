package kr.co.F1FS.app.domain.postRoom.application.port.in;

import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;

public interface DeletePostRoomUseCase {
    void addPostRoomId(Long roomId);
    void delete(PostRoom postRoom);
}
