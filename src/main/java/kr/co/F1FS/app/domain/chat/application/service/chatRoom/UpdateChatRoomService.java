package kr.co.F1FS.app.domain.chat.application.service.chatRoom;

import kr.co.F1FS.app.domain.chat.application.port.in.chatRoom.UpdateChatRoomUseCase;
import kr.co.F1FS.app.domain.chat.application.port.out.ChatRoomJpaPort;
import kr.co.F1FS.app.domain.chat.domain.ChatRoom;
import kr.co.F1FS.app.domain.chat.presentation.dto.ModifyChatRoomDTO;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UpdateChatRoomService implements UpdateChatRoomUseCase {
    private final ChatRoomDomainService chatRoomDomainService;
    private final ChatRoomJpaPort chatRoomJpaPort;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    public void increaseMember(ChatRoom chatRoom) {
        chatRoomDomainService.increaseMember(chatRoom);
        chatRoomJpaPort.saveAndFlush(chatRoom);
    }

    @Override
    public void decreaseMember(ChatRoom chatRoom) {
        chatRoomDomainService.decreaseMember(chatRoom);
        chatRoomJpaPort.saveAndFlush(chatRoom);
    }

    @Override
    public void updateLastChatMessage(ChatRoom chatRoom, LocalDateTime lastEnterTime) {
        chatRoomDomainService.updateLastChatMessage(chatRoom, lastEnterTime);
        chatRoomJpaPort.saveAndFlush(chatRoom);
    }

    @Override
    public void modify(ChatRoom chatRoom, ModifyChatRoomDTO dto) {
        chatRoomDomainService.modify(chatRoom, dto);
        chatRoomJpaPort.saveAndFlush(chatRoom);
    }
}
