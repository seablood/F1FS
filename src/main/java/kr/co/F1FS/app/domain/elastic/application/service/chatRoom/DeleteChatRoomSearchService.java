package kr.co.F1FS.app.domain.elastic.application.service.chatRoom;

import kr.co.F1FS.app.domain.elastic.application.port.in.chatRoom.DeleteChatRoomSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.ChatRoomSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.ChatRoomDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.ChatRoomSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteChatRoomSearchService implements DeleteChatRoomSearchUseCase {
    private final ChatRoomSearchRepository chatRoomSearchRepository;
    private final ChatRoomSearchRepoPort chatRoomSearchRepoPort;

    @Override
    public void delete(ChatRoomDocument document) {
        chatRoomSearchRepoPort.delete(document);
    }
}
