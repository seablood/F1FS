package kr.co.F1FS.app.domain.record.infrastructure.repository;

import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentSeasonRepository extends JpaRepository<CurrentSeason, Long> {
}
