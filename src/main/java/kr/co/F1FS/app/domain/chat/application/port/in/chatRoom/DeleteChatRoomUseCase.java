package kr.co.F1FS.app.domain.chat.application.port.in.chatRoom;

import kr.co.F1FS.app.domain.chat.domain.ChatRoom;

public interface DeleteChatRoomUseCase {
    void delete(ChatRoom chatRoom);
}
