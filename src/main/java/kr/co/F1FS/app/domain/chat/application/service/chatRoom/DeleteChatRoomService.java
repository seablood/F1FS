package kr.co.F1FS.app.domain.chat.application.service.chatRoom;

import kr.co.F1FS.app.domain.chat.application.port.in.chatRoom.DeleteChatRoomUseCase;
import kr.co.F1FS.app.domain.chat.application.port.out.ChatRoomJpaPort;
import kr.co.F1FS.app.domain.chat.domain.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteChatRoomService implements DeleteChatRoomUseCase {
    private final ChatRoomJpaPort chatRoomJpaPort;

    @Override
    public void delete(ChatRoom chatRoom) {
        chatRoomJpaPort.delete(chatRoom);
    }
}
