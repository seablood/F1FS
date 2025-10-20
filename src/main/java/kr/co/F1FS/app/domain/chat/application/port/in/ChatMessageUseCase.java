package kr.co.F1FS.app.domain.chat.application.port.in;

import kr.co.F1FS.app.domain.chat.presentation.dto.CreateChatMessageDTO;
import kr.co.F1FS.app.global.presentation.dto.chat.ResponseChatMessageDTO;

import java.util.List;

public interface ChatMessageUseCase {
    ResponseChatMessageDTO enterChatRoom(Long roomId, CreateChatMessageDTO dto, String username);
    ResponseChatMessageDTO sendMessage(Long roomId, CreateChatMessageDTO dto, String username);
    ResponseChatMessageDTO leaveChatRoom(Long roomId, CreateChatMessageDTO dto, String username);
    List<ResponseChatMessageDTO> getMessageList(Long roomId, String username);
}
