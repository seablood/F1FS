package kr.co.F1FS.app.domain.chat.application.port.in.chatRoom;

import kr.co.F1FS.app.domain.chat.domain.ChatRoom;
import kr.co.F1FS.app.global.presentation.dto.chat.ResponseChatRoomDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QueryChatRoomUseCase {
    ChatRoom findById(Long roomId);
    Page<ResponseChatRoomDTO> findAll(Pageable pageable);
    Page<ResponseChatRoomDTO> findByIdIn(List<Long> roomIds, Pageable pageable);
}
