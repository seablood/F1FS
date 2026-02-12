package kr.co.F1FS.app.domain.chat.application.port.in.chatMessage;

import kr.co.F1FS.app.domain.chat.domain.ChatMessage;
import kr.co.F1FS.app.domain.chat.presentation.dto.chatMessage.CreateChatMessageDTO;

public interface CreateChatMessageUseCase {
    ChatMessage save(Long roomId, CreateChatMessageDTO dto, String username);
}
