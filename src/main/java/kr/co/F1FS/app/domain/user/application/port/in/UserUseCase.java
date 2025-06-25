package kr.co.F1FS.app.domain.user.application.port.in;

import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.user.presentation.dto.ModifyNicknameDTO;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDTO;
import kr.co.F1FS.app.global.util.Role;

public interface UserUseCase {
    ResponseUserDTO findByNickname(String nickname);
    User findByNicknameNotDTO(String nickname);
    User findByUsernameNotDTO(String username);
    ResponseUserDTO modify(User user, ModifyNicknameDTO dto);
    void updateRole(User user, Role newRole);
    void suspendUser(User user, Integer durationDays);
    void updateLastLoginDate(User user);
    void updatePassword(User user, String password);
    void updateRefreshToken(User user, String refreshToken);
}
