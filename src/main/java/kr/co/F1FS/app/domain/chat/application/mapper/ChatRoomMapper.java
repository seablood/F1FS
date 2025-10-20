package kr.co.F1FS.app.domain.chat.application.mapper;

import kr.co.F1FS.app.domain.chat.domain.ChatRoom;
import kr.co.F1FS.app.domain.chat.presentation.dto.CreateChatRoomDTO;
import kr.co.F1FS.app.global.presentation.dto.chat.ResponseChatRoomDTO;
import org.springframework.stereotype.Component;

@Component
public class ChatRoomMapper {
    public ChatRoom toChatRoom(CreateChatRoomDTO dto, String masterUser){
        return ChatRoom.builder()
                .name(dto.getName())
                .masterUser(masterUser)
                .description(dto.getDescription())
                .build();
    }

    public ResponseChatRoomDTO toResponseChatRoomDTO(ChatRoom chatRoom){
        return ResponseChatRoomDTO.builder()
                .id(chatRoom.getId())
                .name(chatRoom.getName())
                .description(chatRoom.getDescription())
                .memberCount(chatRoom.getMemberCount())
                .build();
    }
}
