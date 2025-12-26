package kr.co.F1FS.app.domain.elastic.application.port.in.chatRoom;

import kr.co.F1FS.app.domain.elastic.domain.ChatRoomDocument;

public interface DeleteChatRoomSearchUseCase {
    void delete(ChatRoomDocument document);
}
