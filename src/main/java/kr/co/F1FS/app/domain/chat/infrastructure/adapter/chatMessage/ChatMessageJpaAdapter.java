package kr.co.F1FS.app.domain.chat.infrastructure.adapter.chatMessage;

import kr.co.F1FS.app.domain.chat.application.port.out.chatMessage.ChatMessageJpaPort;
import kr.co.F1FS.app.domain.chat.domain.ChatMessage;
import kr.co.F1FS.app.domain.chat.infrastructure.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatMessageJpaAdapter implements ChatMessageJpaPort {
    private final ChatMessageRepository chatMessageRepository;

    @Override
    public ChatMessage save(ChatMessage chatMessage) {
        return chatMessageRepository.save(chatMessage);
    }

    @Override
    public List<ChatMessage> findByRoomIdAndSendTimeGreaterThanEqualOrderBySendTimeAsc(Long roomId, LocalDateTime lastEnterTime) {
        return chatMessageRepository.findByRoomIdAndSendTimeGreaterThanEqualOrderBySendTimeAsc(roomId, lastEnterTime);
    }
}
