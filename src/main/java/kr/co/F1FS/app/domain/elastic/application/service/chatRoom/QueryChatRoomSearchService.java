package kr.co.F1FS.app.domain.elastic.application.service.chatRoom;

import kr.co.F1FS.app.domain.elastic.application.port.in.chatRoom.QueryChatRoomSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.ChatRoomSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.ChatRoomDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.ChatRoomSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryChatRoomSearchService implements QueryChatRoomSearchUseCase {
    private final ChatRoomSearchRepository chatRoomSearchRepository;
    private final ChatRoomSearchRepoPort chatRoomSearchRepoPort;

    @Override
    public ChatRoomDocument findById(Long id) {
        return chatRoomSearchRepoPort.findById(id);
    }
}
