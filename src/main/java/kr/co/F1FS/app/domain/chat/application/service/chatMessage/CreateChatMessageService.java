package kr.co.F1FS.app.domain.chat.application.service.chatMessage;

import kr.co.F1FS.app.domain.chat.application.port.in.chatMessage.CreateChatMessageUseCase;
import kr.co.F1FS.app.domain.chat.application.port.out.chatMessage.ChatMessageJpaPort;
import kr.co.F1FS.app.domain.chat.domain.ChatMessage;
import kr.co.F1FS.app.domain.chat.presentation.dto.chatMessage.CreateChatMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateChatMessageService implements CreateChatMessageUseCase {
    private final ChatMessageJpaPort chatMessageJpaPort;
    private final ChatMessageDomainService chatMessageDomainService;

    @Override
    public ChatMessage save(Long roomId, CreateChatMessageDTO dto, String username) {
        ChatMessage chatMessage = chatMessageDomainService.createEntity(roomId, dto, username);

        return chatMessageJpaPort.save(chatMessage);
    }
}
