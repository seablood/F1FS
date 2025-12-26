package kr.co.F1FS.app.domain.elastic.application.port.in.chatRoom;

import kr.co.F1FS.app.domain.chat.domain.ChatRoom;

public interface CreateChatRoomSearchUseCase {
    void save(ChatRoom chatRoom);
}
