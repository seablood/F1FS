package kr.co.F1FS.app.domain.admin.user.application.port.out;

import kr.co.F1FS.app.domain.complain.user.domain.UserComplain;
import kr.co.F1FS.app.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminUserComplainPort {
    Page<UserComplain> getComplainByUser(User toUser, Pageable pageable);
    List<UserComplain> getComplainByUser(User toUser);
    void delete(UserComplain userComplain);
}
