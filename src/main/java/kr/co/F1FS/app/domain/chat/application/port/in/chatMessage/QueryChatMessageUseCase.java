package kr.co.F1FS.app.domain.chat.application.port.in.chatMessage;

import kr.co.F1FS.app.global.presentation.dto.chat.ResponseChatMessageDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface QueryChatMessageUseCase {
    List<ResponseChatMessageDTO> findByRoomIdAndSendTimeGreaterThanEqualOrderBySendTimeAsc(Long roomId, LocalDateTime lastEnterTime);
}
