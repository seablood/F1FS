package kr.co.F1FS.app.domain.session.infrastructure.repository;

import kr.co.F1FS.app.domain.session.domain.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
}
