package kr.co.F1FS.app.domain.suggest.infrastructure.repository;

import kr.co.F1FS.app.domain.suggest.domain.Suggest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuggestRepository extends JpaRepository<Suggest, Long> {
}
