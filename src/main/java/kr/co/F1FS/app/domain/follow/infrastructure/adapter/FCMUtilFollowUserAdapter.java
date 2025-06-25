package kr.co.F1FS.app.domain.follow.infrastructure.adapter;

import kr.co.F1FS.app.domain.follow.application.service.FollowUserService;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.application.port.out.FCMUtilFollowUserPort;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserIdDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FCMUtilFollowUserAdapter implements FCMUtilFollowUserPort {
    private final FollowUserService followUserService;

    @Override
    public List<ResponseUserIdDTO> findFollowersNotDTO(User user) {
        return followUserService.findFollowersNotDTO(user);
    }
}
