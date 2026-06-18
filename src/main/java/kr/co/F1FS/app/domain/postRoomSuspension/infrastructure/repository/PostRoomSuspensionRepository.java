package kr.co.F1FS.app.domain.postRoomSuspension.infrastructure.repository;

import kr.co.F1FS.app.domain.postRoomSuspension.domain.PostRoomSuspension;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRoomSuspensionRepository extends JpaRepository<PostRoomSuspension, Long> {
}
