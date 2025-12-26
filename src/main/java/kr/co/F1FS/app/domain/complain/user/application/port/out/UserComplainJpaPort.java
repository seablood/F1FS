package kr.co.F1FS.app.domain.complain.user.application.port.out;

import kr.co.F1FS.app.domain.complain.user.domain.UserComplain;
import kr.co.F1FS.app.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserComplainJpaPort {
    UserComplain save(UserComplain userComplain);
    Page<UserComplain> findAll(Pageable pageable);
    UserComplain findById(Long id);
    Page<UserComplain> findAllByToUser(User toUser, Pageable pageable);
    Page<UserComplain> findAllByFromUser(User fromUser, Pageable pageable);
    List<UserComplain> findAllByToUser(User toUser);
    void delete(UserComplain userComplain);
}
