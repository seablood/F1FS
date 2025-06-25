package kr.co.F1FS.app.domain.suggest.infrastructure.repository;

import kr.co.F1FS.app.domain.suggest.domain.Suggest;
import kr.co.F1FS.app.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuggestRepository extends JpaRepository<Suggest, Long> {
    Page<Suggest> findAllByFromUser(User user, Pageable pageable);
    Page<Suggest> findAll(Pageable pageable);
}
