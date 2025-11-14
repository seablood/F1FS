package kr.co.F1FS.app.domain.complain.user.application.port.out;

import kr.co.F1FS.app.domain.admin.user.presentation.dto.AdminResponseUserComplainDTO;
import kr.co.F1FS.app.domain.complain.user.domain.UserComplain;
import kr.co.F1FS.app.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserComplainJpaPort {
    void save(UserComplain userComplain);
    Page<AdminResponseUserComplainDTO> findAll(Pageable pageable);
    Page<UserComplain> findAllByToUser(User toUser, Pageable pageable);
    List<UserComplain> findAllByToUser(User toUser);
    void delete(UserComplain userComplain);
}
