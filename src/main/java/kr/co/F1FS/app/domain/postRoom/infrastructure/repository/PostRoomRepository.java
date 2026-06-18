package kr.co.F1FS.app.domain.postRoom.infrastructure.repository;

import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRoomRepository extends JpaRepository<PostRoom, Long> {
}
