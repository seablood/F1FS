package kr.co.F1FS.app.domain.user.application.port.in;

import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.user.presentation.dto.ModifyDescriptionDTO;
import kr.co.F1FS.app.domain.user.presentation.dto.ModifyNicknameDTO;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDTO;
import kr.co.F1FS.app.global.util.Role;

public interface UpdateUserUseCase {
    void updateLastLoginDate(User user);
    void updateRole(User user, Role role);
    void updatePassword(User user, String password);
    ResponseUserDTO updateNickname(User user, ModifyNicknameDTO dto);
    ResponseUserDTO updateDescription(User user, ModifyDescriptionDTO dto);
    void updateRefreshToken(User user, String refreshToken);
    void increaseFollow(User followerUser, User followeeUser);
    void decreaseFollow(User followerUser, User followeeUser);
    void suspendUser(User user, Integer durationDays);
}
