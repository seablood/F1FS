package kr.co.F1FS.app.global.application.port.out;

import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserIdDTO;

import java.util.List;

public interface FCMUtilFollowUserPort {
    List<ResponseUserIdDTO> findFollowersNotDTO(User user);
}
