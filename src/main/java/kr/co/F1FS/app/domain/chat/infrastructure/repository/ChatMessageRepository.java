package kr.co.F1FS.app.domain.chat.infrastructure.repository;

import kr.co.F1FS.app.domain.chat.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByRoomIdAndSendTimeGreaterThanEqualOrderBySendTimeAsc(Long roomId, LocalDateTime lastEnterTime);
}
