package kr.co.F1FS.app.domain.chat.infrastructure.adapter;

import kr.co.F1FS.app.domain.chat.application.mapper.ChatMessageMapper;
import kr.co.F1FS.app.domain.chat.application.port.out.ChatMessageJpaPort;
import kr.co.F1FS.app.domain.chat.domain.ChatMessage;
import kr.co.F1FS.app.domain.chat.infrastructure.repository.ChatMessageRepository;
import kr.co.F1FS.app.global.presentation.dto.chat.ResponseChatMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatMessageJpaAdapter implements ChatMessageJpaPort {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageMapper chatMessageMapper;

    @Override
    public void save(ChatMessage chatMessage) {
        chatMessageRepository.save(chatMessage);
    }

    @Override
    public List<ChatMessage> findByRoomIdAndSendTimeGreaterThanEqualOrderBySendTimeAsc(Long roomId, LocalDateTime lastEnterTime) {
        return chatMessageRepository.findByRoomIdAndSendTimeGreaterThanEqualOrderBySendTimeAsc(roomId, lastEnterTime);
    }
}
