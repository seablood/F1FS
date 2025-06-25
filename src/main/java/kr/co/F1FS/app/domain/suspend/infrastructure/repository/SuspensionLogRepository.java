package kr.co.F1FS.app.domain.suspend.infrastructure.repository;

import kr.co.F1FS.app.domain.suspend.domain.SuspensionLog;
import kr.co.F1FS.app.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SuspensionLogRepository extends JpaRepository<SuspensionLog, Long> {
    Optional<SuspensionLog> findBySuspendUser(User user);
}
