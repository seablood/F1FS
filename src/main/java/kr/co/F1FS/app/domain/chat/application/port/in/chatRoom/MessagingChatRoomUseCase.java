package kr.co.F1FS.app.domain.chat.application.port.in.chatRoom;

import kr.co.F1FS.app.domain.chat.domain.ChatRoom;

import java.time.LocalDateTime;

public interface MessagingChatRoomUseCase {
    void enterChatRoom(ChatRoom chatRoom, String username, LocalDateTime lastEnterTime);
    void sendMessage(ChatRoom chatRoom, LocalDateTime sendTime);
    void leaveChatRoom(ChatRoom chatRoom, String username, LocalDateTime sendTime);
}
