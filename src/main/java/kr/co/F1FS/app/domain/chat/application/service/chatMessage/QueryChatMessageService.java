package kr.co.F1FS.app.domain.chat.application.service.chatMessage;

import kr.co.F1FS.app.domain.chat.application.mapper.chatMessage.ChatMessageMapper;
import kr.co.F1FS.app.domain.chat.application.port.in.chatMessage.QueryChatMessageUseCase;
import kr.co.F1FS.app.domain.chat.application.port.out.chatMessage.ChatMessageJpaPort;
import kr.co.F1FS.app.global.presentation.dto.chat.ResponseChatMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryChatMessageService implements QueryChatMessageUseCase {
    private final ChatMessageJpaPort chatMessageJpaPort;
    private final ChatMessageMapper chatMessageMapper;

    @Override
    public List<ResponseChatMessageDTO> findByRoomIdAndSendTimeGreaterThanEqualOrderBySendTimeAscForDTO(Long roomId, LocalDateTime lastEnterTime) {
        return chatMessageJpaPort.findByRoomIdAndSendTimeGreaterThanEqualOrderBySendTimeAsc(roomId, lastEnterTime)
                .stream()
                .map(chatMessage -> chatMessageMapper.toResponseChatMessageDTO(chatMessage))
                .toList();
    }
}
