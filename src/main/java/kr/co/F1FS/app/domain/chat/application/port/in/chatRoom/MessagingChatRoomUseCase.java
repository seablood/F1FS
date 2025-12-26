package kr.co.F1FS.app.domain.chat.application.port.in.chatRoom;

import java.time.LocalDateTime;

public interface MessagingChatRoomUseCase {
    boolean enterChatRoom(Long roomId, String username, LocalDateTime lastEnterTime);
    void sendMessage(Long roomId, LocalDateTime sendTime);
    void leaveChatRoom(Long roomId, String username, LocalDateTime sendTime);
}
