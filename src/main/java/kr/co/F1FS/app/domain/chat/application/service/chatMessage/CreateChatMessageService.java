package kr.co.F1FS.app.domain.chat.application.service.chatMessage;

import kr.co.F1FS.app.domain.chat.application.mapper.ChatMessageMapper;
import kr.co.F1FS.app.domain.chat.application.port.in.chatMessage.CreateChatMessageUseCase;
import kr.co.F1FS.app.domain.chat.application.port.out.ChatMessageJpaPort;
import kr.co.F1FS.app.domain.chat.domain.ChatMessage;
import kr.co.F1FS.app.global.presentation.dto.chat.ResponseChatMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateChatMessageService implements CreateChatMessageUseCase {
    private final ChatMessageJpaPort chatMessageJpaPort;
    private final ChatMessageMapper chatMessageMapper;

    @Override
    public ResponseChatMessageDTO save(ChatMessage chatMessage) {
        chatMessageJpaPort.save(chatMessage);
        return chatMessageMapper.toResponseChatMessageDTO(chatMessage);
    }
}
