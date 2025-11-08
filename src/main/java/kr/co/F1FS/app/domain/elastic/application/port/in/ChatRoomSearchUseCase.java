package kr.co.F1FS.app.domain.elastic.application.port.in;

import kr.co.F1FS.app.domain.chat.domain.ChatRoom;
import kr.co.F1FS.app.domain.elastic.domain.ChatRoomDocument;
import kr.co.F1FS.app.global.presentation.dto.chat.ResponseChatRoomDocumentDTO;
import org.springframework.data.domain.Page;

public interface ChatRoomSearchUseCase {
    void save(ChatRoom chatRoom);
    void save(ChatRoomDocument chatRoomDocument);
    ChatRoomDocument findById(Long id);
    void modify(ChatRoomDocument document, ChatRoom chatRoom);
    void delete(ChatRoomDocument document);
    void increaseMemberCount(ChatRoomDocument chatRoomDocument);
    void decreaseMemberCount(ChatRoomDocument chatRoomDocument);
    Page<ResponseChatRoomDocumentDTO> getChatRoomList(int page, int size, String condition, String keyword);
}
