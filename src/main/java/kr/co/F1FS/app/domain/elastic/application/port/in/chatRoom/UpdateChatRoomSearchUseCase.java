package kr.co.F1FS.app.domain.elastic.application.port.in.chatRoom;

import kr.co.F1FS.app.domain.chat.domain.ChatRoom;
import kr.co.F1FS.app.domain.elastic.domain.ChatRoomDocument;

public interface UpdateChatRoomSearchUseCase {
    void increaseMemberCount(ChatRoomDocument document);
    void decreaseMemberCount(ChatRoomDocument document);
    void modify(ChatRoomDocument document, ChatRoom chatRoom);
}
