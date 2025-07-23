package kr.co.F1FS.app.domain.grandprix.infrastructure.repository;

import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrandPrixRepository extends JpaRepository<GrandPrix, Long> {
    Page<GrandPrix> findGrandPrixesBySeason(Integer season, Pageable pageable);
}
