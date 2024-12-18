package kr.co.F1FS.app.repository;

import kr.co.F1FS.app.model.CurrentSeason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentSeasonRepository extends JpaRepository<CurrentSeason, Long> {
}
