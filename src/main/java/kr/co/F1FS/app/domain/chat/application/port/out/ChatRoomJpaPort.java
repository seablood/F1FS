package kr.co.F1FS.app.domain.chat.application.port.out;

import kr.co.F1FS.app.domain.chat.domain.ChatRoom;
import kr.co.F1FS.app.global.presentation.dto.chat.ResponseChatRoomDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChatRoomJpaPort {
    void save(ChatRoom chatRoom);
    void saveAndFlush(ChatRoom chatRoom);
    Page<ChatRoom> findAll(Pageable pageable);
    ChatRoom findById(Long roomId);
    Page<ChatRoom> findByIdIn(List<Long> roomIds, Pageable pageable);
    void delete(ChatRoom chatRoom);

}
