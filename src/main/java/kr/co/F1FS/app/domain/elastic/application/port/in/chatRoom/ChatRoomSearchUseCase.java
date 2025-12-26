package kr.co.F1FS.app.domain.elastic.application.port.in.chatRoom;

import kr.co.F1FS.app.domain.chat.domain.ChatRoom;
import kr.co.F1FS.app.domain.elastic.domain.ChatRoomDocument;
import kr.co.F1FS.app.global.presentation.dto.chat.ResponseChatRoomDocumentDTO;
import org.springframework.data.domain.Page;

public interface ChatRoomSearchUseCase {
    Page<ResponseChatRoomDocumentDTO> getChatRoomList(int page, int size, String condition, String keyword);
}
