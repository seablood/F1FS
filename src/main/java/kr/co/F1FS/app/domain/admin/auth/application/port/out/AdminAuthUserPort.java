package kr.co.F1FS.app.domain.admin.auth.application.port.out;

import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.Role;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminAuthUserPort {
    User save(User user);
    void saveAllAndFlush(List<User> list);
    List<User> findAllByLastLoginDateBeforeOrLastLoginDateIsNull(LocalDateTime sixMonthAgo);
}
