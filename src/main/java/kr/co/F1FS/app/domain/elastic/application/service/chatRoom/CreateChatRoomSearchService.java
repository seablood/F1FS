package kr.co.F1FS.app.domain.elastic.application.service.chatRoom;

import kr.co.F1FS.app.domain.chat.domain.ChatRoom;
import kr.co.F1FS.app.domain.elastic.application.port.in.chatRoom.CreateChatRoomSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.ChatRoomSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.ChatRoomDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.ChatRoomSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateChatRoomSearchService implements CreateChatRoomSearchUseCase {
    private final ChatRoomSearchRepository chatRoomSearchRepository;
    private final ChatRoomSearchRepoPort chatRoomSearchRepoPort;

    @Override
    public void save(ChatRoom chatRoom) {
        ChatRoomDocument chatRoomDocument = ChatRoomDocument.builder()
                .chatRoom(chatRoom).build();

        chatRoomSearchRepoPort.save(chatRoomDocument);
    }
}
