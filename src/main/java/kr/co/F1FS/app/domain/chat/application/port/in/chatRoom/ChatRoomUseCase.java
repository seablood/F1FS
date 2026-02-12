package kr.co.F1FS.app.domain.chat.application.port.in.chatRoom;

import kr.co.F1FS.app.domain.chat.presentation.dto.chatRoom.CreateChatRoomDTO;
import kr.co.F1FS.app.domain.chat.presentation.dto.chatRoom.ModifyChatRoomDTO;
import kr.co.F1FS.app.global.presentation.dto.chat.ResponseChatRoomDTO;
import org.springframework.data.domain.Page;

public interface ChatRoomUseCase {
    void save(CreateChatRoomDTO dto, String masterUser);
    Page<ResponseChatRoomDTO> getChatRoomAll(int page, int size, String condition);
    Page<ResponseChatRoomDTO> getChatRoomListByIdIn(int page, int size, String condition, String username);
    void modify(Long roomId, ModifyChatRoomDTO dto, String username);
    void delete(Long roomId, String username);
}
