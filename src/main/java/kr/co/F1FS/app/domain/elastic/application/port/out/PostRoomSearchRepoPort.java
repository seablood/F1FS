package kr.co.F1FS.app.domain.elastic.application.port.out;

import kr.co.F1FS.app.domain.elastic.domain.PostRoomDocument;

public interface PostRoomSearchRepoPort {
    void save(PostRoomDocument document);
    PostRoomDocument findById(Long id);
    void delete(PostRoomDocument document);
}
