package kr.co.F1FS.app.domain.repository.rdb.user;

import kr.co.F1FS.app.domain.model.rdb.UserComplain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserComplainRepository extends JpaRepository<UserComplain, Long> {
    Page<UserComplain> findAll(Pageable pageable);
}
