package kr.co.F1FS.app.domain.complain.user.infrastructure.repository;

import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.complain.user.domain.UserComplain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserComplainRepository extends JpaRepository<UserComplain, Long> {
    List<UserComplain> findAllByToUser(User user);
}
