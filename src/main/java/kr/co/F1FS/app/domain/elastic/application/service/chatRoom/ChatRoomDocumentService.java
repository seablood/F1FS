package kr.co.F1FS.app.domain.elastic.application.service.chatRoom;

import kr.co.F1FS.app.domain.chat.domain.ChatRoom;
import kr.co.F1FS.app.domain.elastic.domain.ChatRoomDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomDocumentService {
    public void increaseMemberCount(ChatRoomDocument document){
        document.increaseMemberCount();
    }

    public void decreaseMemberCount(ChatRoomDocument document){
        document.decreaseMemberCount();
    }

    public void modify(ChatRoomDocument document, ChatRoom chatRoom){
        document.modify(chatRoom);
    }
}
