package kr.co.F1FS.app.domain.chat.application.service.chatRoom;

import kr.co.F1FS.app.domain.chat.application.mapper.ChatRoomMapper;
import kr.co.F1FS.app.domain.chat.domain.ChatRoom;
import kr.co.F1FS.app.domain.chat.presentation.dto.CreateChatRoomDTO;
import kr.co.F1FS.app.domain.chat.presentation.dto.ModifyChatRoomDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatRoomDomainService {
    private final ChatRoomMapper chatRoomMapper;

    public ChatRoom createEntity(CreateChatRoomDTO dto, String masterUser){
        return chatRoomMapper.toChatRoom(dto, masterUser);
    }

    public void increaseMember(ChatRoom chatRoom){
        chatRoom.increaseMember();
    }

    public void decreaseMember(ChatRoom chatRoom){
        chatRoom.decreaseMember();
    }

    public void updateLastChatMessage(ChatRoom chatRoom, LocalDateTime lastEnterTime){
        chatRoom.updateLastChatMessage(lastEnterTime);
    }

    public void modify(ChatRoom chatRoom, ModifyChatRoomDTO dto){
        chatRoom.modify(dto);
    }
}
