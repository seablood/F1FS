package kr.co.F1FS.app.domain.grandprix.infrastructure.repository;

import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrandPrixRepository extends JpaRepository<GrandPrix, Long> {
    List<GrandPrix> findGrandPrixesBySeason(Integer season);
}
