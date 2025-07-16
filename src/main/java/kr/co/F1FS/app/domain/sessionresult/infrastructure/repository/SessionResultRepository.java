package kr.co.F1FS.app.domain.sessionresult.infrastructure.repository;

import kr.co.F1FS.app.domain.sessionresult.domain.SessionResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionResultRepository extends JpaRepository<SessionResult, Long> {
}
