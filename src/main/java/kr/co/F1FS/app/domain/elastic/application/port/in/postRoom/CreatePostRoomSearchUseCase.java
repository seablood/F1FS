package kr.co.F1FS.app.domain.elastic.application.port.in.postRoom;

import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;

public interface CreatePostRoomSearchUseCase {
    void save(PostRoom postRoom);
}
