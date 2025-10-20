package kr.co.F1FS.app.domain.chat.application.port.in;

import kr.co.F1FS.app.domain.chat.presentation.dto.CreateChatRoomDTO;
import kr.co.F1FS.app.domain.chat.presentation.dto.ModifyChatRoomDTO;
import kr.co.F1FS.app.global.presentation.dto.chat.ResponseChatRoomDTO;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public interface ChatRoomUseCase {
    void save(CreateChatRoomDTO dto, String masterUser);
    Page<ResponseChatRoomDTO> findAll(int page, int size, String condition);
    Page<ResponseChatRoomDTO> findSubscribeChatRoom(int page, int size, String condition, String username);
    boolean enterChatRoom(Long roomId, String username, LocalDateTime lastEnterTime);
    void sendMessage(Long roomId, LocalDateTime sendTime);
    void leaveChatRoom(Long roomId, String username, LocalDateTime sendTime);
    void modify(Long roomId, ModifyChatRoomDTO dto, String username);
    void delete(Long roomId, String username);
}
