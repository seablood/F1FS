package kr.co.F1FS.app.domain.repository.rdb.suspend;

import kr.co.F1FS.app.domain.model.rdb.SuspensionLog;
import kr.co.F1FS.app.domain.model.rdb.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SuspensionLogRepository extends JpaRepository<SuspensionLog, Long> {
    Optional<SuspensionLog> findBySuspendUser(User user);
}
