package kr.co.F1FS.app.domain.chat.application.port.in.chatRoom;

import kr.co.F1FS.app.domain.chat.domain.ChatRoom;
import kr.co.F1FS.app.domain.chat.presentation.dto.ModifyChatRoomDTO;

import java.time.LocalDateTime;

public interface UpdateChatRoomUseCase {
    void increaseMember(ChatRoom chatRoom);
    void decreaseMember(ChatRoom chatRoom);
    void updateLastChatMessage(ChatRoom chatRoom, LocalDateTime lastEnterTime);
    void modify(ChatRoom chatRoom, ModifyChatRoomDTO dto);
}
