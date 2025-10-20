package kr.co.F1FS.app.domain.chat.application.mapper;

import kr.co.F1FS.app.domain.chat.domain.ChatMessage;
import kr.co.F1FS.app.domain.chat.presentation.dto.CreateChatMessageDTO;
import kr.co.F1FS.app.global.presentation.dto.chat.ResponseChatMessageDTO;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class ChatMessageMapper {
    public ChatMessage toChatMessage(Long roomId, CreateChatMessageDTO dto, String username){
        return ChatMessage.builder()
                .roomId(roomId)
                .sender(username)
                .content(dto.getContent())
                .imageUrl(dto.getImageUrl())
                .chatStatus(dto.getChatStatus())
                .build();
    }

    public ResponseChatMessageDTO toResponseChatMessageDTO(ChatMessage chatMessage){
        return ResponseChatMessageDTO.builder()
                .sender(chatMessage.getSender())
                .content(chatMessage.getContent())
                .imageUrl(chatMessage.getImageUrl())
                .sendTime(chatMessage.getSendTime().format(DateTimeFormatter.ofPattern("HH:mm")))
                .build();
    }
}
