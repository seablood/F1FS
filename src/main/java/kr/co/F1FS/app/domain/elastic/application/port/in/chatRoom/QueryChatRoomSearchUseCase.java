package kr.co.F1FS.app.domain.elastic.application.port.in.chatRoom;

import kr.co.F1FS.app.domain.elastic.domain.ChatRoomDocument;

public interface QueryChatRoomSearchUseCase {
    ChatRoomDocument findById(Long id);
}
