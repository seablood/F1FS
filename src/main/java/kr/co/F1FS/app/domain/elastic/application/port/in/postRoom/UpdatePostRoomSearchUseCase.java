package kr.co.F1FS.app.domain.elastic.application.port.in.postRoom;

import kr.co.F1FS.app.domain.elastic.domain.PostRoomDocument;
import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;

public interface UpdatePostRoomSearchUseCase {
    void modifyInfo(PostRoom postRoom, PostRoomDocument document);
    void modifyIsPublic(PostRoom postRoom, PostRoomDocument document);
}
