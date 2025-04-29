package kr.co.F1FS.app.domain.repository.rdb.notification;

import kr.co.F1FS.app.domain.model.rdb.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
