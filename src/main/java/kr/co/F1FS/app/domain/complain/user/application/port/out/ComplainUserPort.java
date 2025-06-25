package kr.co.F1FS.app.domain.complain.user.application.port.out;

import kr.co.F1FS.app.domain.user.domain.User;

public interface ComplainUserPort {
    User findByNickname(String nickname);
}
