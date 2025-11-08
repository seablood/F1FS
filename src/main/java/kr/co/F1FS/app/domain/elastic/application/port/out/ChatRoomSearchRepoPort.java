package kr.co.F1FS.app.domain.elastic.application.port.out;

import kr.co.F1FS.app.domain.elastic.domain.ChatRoomDocument;

public interface ChatRoomSearchRepoPort {
    void save(ChatRoomDocument document);
    ChatRoomDocument findById(Long id);
    void delete(ChatRoomDocument document);
}
