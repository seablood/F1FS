package kr.co.F1FS.app.domain.elastic.application.port.in.postRoom;

import kr.co.F1FS.app.domain.elastic.domain.PostRoomDocument;

public interface DeletePostRoomSearchUseCase {
    void delete(PostRoomDocument document);
}
