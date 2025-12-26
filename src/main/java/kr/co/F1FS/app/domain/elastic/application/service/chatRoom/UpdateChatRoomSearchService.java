package kr.co.F1FS.app.domain.elastic.application.service.chatRoom;

import kr.co.F1FS.app.domain.chat.domain.ChatRoom;
import kr.co.F1FS.app.domain.elastic.application.port.in.chatRoom.UpdateChatRoomSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.out.ChatRoomSearchRepoPort;
import kr.co.F1FS.app.domain.elastic.domain.ChatRoomDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.ChatRoomSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateChatRoomSearchService implements UpdateChatRoomSearchUseCase {
    private final ChatRoomSearchRepository chatRoomSearchRepository;
    private final ChatRoomSearchRepoPort chatRoomSearchRepoPort;
    private final ChatRoomDocumentService chatRoomDocumentService;

    @Override
    public void increaseMemberCount(ChatRoomDocument document) {
        chatRoomDocumentService.increaseMemberCount(document);
        chatRoomSearchRepoPort.save(document);
    }

    @Override
    public void decreaseMemberCount(ChatRoomDocument document) {
        chatRoomDocumentService.decreaseMemberCount(document);
        chatRoomSearchRepoPort.save(document);
    }

    @Override
    public void modify(ChatRoomDocument document, ChatRoom chatRoom) {
        chatRoomDocumentService.modify(document, chatRoom);
        chatRoomSearchRepoPort.save(document);
    }
}
