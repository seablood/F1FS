package kr.co.F1FS.app.domain.chat.application.port.in.chatMessage;

import kr.co.F1FS.app.domain.chat.domain.ChatMessage;
import kr.co.F1FS.app.global.presentation.dto.chat.ResponseChatMessageDTO;

public interface CreateChatMessageUseCase {
    ResponseChatMessageDTO save(ChatMessage chatMessage);
}
