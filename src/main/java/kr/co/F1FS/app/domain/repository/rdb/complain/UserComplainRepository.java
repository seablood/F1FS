package kr.co.F1FS.app.domain.repository.rdb.complain;

import kr.co.F1FS.app.domain.model.rdb.User;
import kr.co.F1FS.app.domain.model.rdb.UserComplain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserComplainRepository extends JpaRepository<UserComplain, Long> {
    Page<UserComplain> findAll(Pageable pageable);
    Page<UserComplain> findAllByToUser(User user, Pageable pageable);
    List<UserComplain> findAllByToUser(User user);
}
