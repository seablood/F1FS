package kr.co.F1FS.app.domain.elastic.application.port.in.postRoom;

import kr.co.F1FS.app.domain.elastic.domain.PostRoomDocument;

public interface QueryPostRoomSearchUseCase {
    PostRoomDocument findById(Long id);
}
