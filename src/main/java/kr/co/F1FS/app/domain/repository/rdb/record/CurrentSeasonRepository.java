package kr.co.F1FS.app.domain.repository.rdb.record;

import kr.co.F1FS.app.domain.model.rdb.CurrentSeason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentSeasonRepository extends JpaRepository<CurrentSeason, Long> {
}
