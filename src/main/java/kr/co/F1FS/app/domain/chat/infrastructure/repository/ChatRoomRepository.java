package kr.co.F1FS.app.domain.chat.infrastructure.repository;

import kr.co.F1FS.app.domain.chat.domain.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Page<ChatRoom> findAll(Pageable pageable);
    Page<ChatRoom> findByIdIn(List<Long> roomIds, Pageable pageable);
}
