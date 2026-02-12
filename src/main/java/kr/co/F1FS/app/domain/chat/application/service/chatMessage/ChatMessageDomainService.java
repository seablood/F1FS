package kr.co.F1FS.app.domain.chat.application.service.chatMessage;

import kr.co.F1FS.app.domain.chat.application.mapper.chatMessage.ChatMessageMapper;
import kr.co.F1FS.app.domain.chat.domain.ChatMessage;
import kr.co.F1FS.app.domain.chat.presentation.dto.chatMessage.CreateChatMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageDomainService {
    private final ChatMessageMapper chatMessageMapper;

    public ChatMessage createEntity(Long roomId, CreateChatMessageDTO dto, String username){
        return chatMessageMapper.toChatMessage(roomId, dto, username);
    }
}
