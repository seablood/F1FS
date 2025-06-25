package kr.co.F1FS.app.domain.admin.user.application.port.out;

import kr.co.F1FS.app.domain.user.domain.User;

public interface AdminUserPort {
    void saveAndFlush(User user);
}
