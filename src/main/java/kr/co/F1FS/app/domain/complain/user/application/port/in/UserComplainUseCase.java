package kr.co.F1FS.app.domain.complain.user.application.port.in;

import kr.co.F1FS.app.domain.complain.user.domain.UserComplain;
import kr.co.F1FS.app.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserComplainUseCase {
    void save(UserComplain complain);
    Page<UserComplain> findAll(Pageable pageable);
}
