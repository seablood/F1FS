package kr.co.F1FS.app.domain.admin.post.application.port.out;

import kr.co.F1FS.app.domain.user.domain.User;

public interface AdminPostUserPort {
    User findByNickname(String nickname);
}
