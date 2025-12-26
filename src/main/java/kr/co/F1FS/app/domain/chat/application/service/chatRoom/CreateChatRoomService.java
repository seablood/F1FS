package kr.co.F1FS.app.domain.chat.application.service.chatRoom;

import kr.co.F1FS.app.domain.chat.application.port.in.chatRoom.CreateChatRoomUseCase;
import kr.co.F1FS.app.domain.chat.application.port.out.ChatRoomJpaPort;
import kr.co.F1FS.app.domain.chat.domain.ChatRoom;
import kr.co.F1FS.app.domain.chat.presentation.dto.CreateChatRoomDTO;
import kr.co.F1FS.app.domain.elastic.application.port.in.chatRoom.CreateChatRoomSearchUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateChatRoomService implements CreateChatRoomUseCase {
    private final ChatRoomJpaPort chatRoomJpaPort;
    private final ChatRoomDomainService chatRoomDomainService;
    private final CreateChatRoomSearchUseCase createChatRoomSearchUseCase;

    @Override
    public void createEntity(CreateChatRoomDTO dto, String masterUser) {
        ChatRoom chatRoom = chatRoomDomainService.createEntity(dto, masterUser);

        chatRoomJpaPort.save(chatRoom);
        createChatRoomSearchUseCase.save(chatRoom);
    }
}
