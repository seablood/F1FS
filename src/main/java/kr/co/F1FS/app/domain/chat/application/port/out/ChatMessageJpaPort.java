package kr.co.F1FS.app.domain.chat.application.port.out;

import kr.co.F1FS.app.domain.chat.domain.ChatMessage;
import kr.co.F1FS.app.global.presentation.dto.chat.ResponseChatMessageDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatMessageJpaPort {
    void save(ChatMessage chatMessage);
    List<ResponseChatMessageDTO> findByRoomIdAndSendTimeGreaterThanEqualOrderBySendTimeAsc(Long roomId, LocalDateTime lastEnterTime);
}
