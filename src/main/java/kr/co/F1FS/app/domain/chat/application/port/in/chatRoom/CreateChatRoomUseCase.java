package kr.co.F1FS.app.domain.chat.application.port.in.chatRoom;

import kr.co.F1FS.app.domain.chat.presentation.dto.CreateChatRoomDTO;

public interface CreateChatRoomUseCase {
    void createEntity(CreateChatRoomDTO dto, String masterUser);
}
