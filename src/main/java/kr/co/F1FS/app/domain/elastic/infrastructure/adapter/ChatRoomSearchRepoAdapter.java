package kr.co.F1FS.app.domain.elastic.infrastructure.adapter;

import kr.co.F1FS.app.domain.elastic.application.port.out.ChatRoomSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.ChatRoomDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.ChatRoomSearchRepository;
import kr.co.F1FS.app.global.util.exception.chat.ChatRoomException;
import kr.co.F1FS.app.global.util.exception.chat.ChatRoomExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatRoomSearchRepoAdapter implements ChatRoomSearchRepoPort {
    private final ChatRoomSearchRepository chatRoomSearchRepository;

    @Override
    public void save(ChatRoomDocument document) {
        chatRoomSearchRepository.save(document);
    }

    @Override
    public ChatRoomDocument findById(Long id) {
        return chatRoomSearchRepository.findById(id)
                .orElseThrow(() -> new ChatRoomException(ChatRoomExceptionType.CHAT_ROOM_NOT_FOUND));
    }

    @Override
    public void delete(ChatRoomDocument document) {
        chatRoomSearchRepository.delete(document);
    }
}
