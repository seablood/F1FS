package kr.co.F1FS.app.domain.user.infrastructure.adapter;

import kr.co.F1FS.app.domain.complain.user.application.port.out.ComplainUserPort;
import kr.co.F1FS.app.domain.user.application.service.UserService;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ComplainUserAdapter implements ComplainUserPort {
    private final UserService userService;

    @Override
    public User findByNickname(String nickname) {
        return userService.findByNicknameNotDTO(nickname);
    }
}
