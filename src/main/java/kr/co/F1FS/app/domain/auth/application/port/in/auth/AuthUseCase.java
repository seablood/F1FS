package kr.co.F1FS.app.domain.auth.application.port.in.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.auth.presentation.dto.CreateUserDTO;
import kr.co.F1FS.app.domain.auth.presentation.dto.ModifyPasswordDTO;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDTO;

public interface AuthUseCase {
    ResponseUserDTO save(CreateUserDTO userDTO);
    void updatePassword(User user, ModifyPasswordDTO passwordDTO);
    void logout(String accessToken, String refreshToken, HttpServletRequest request, HttpServletResponse response,
                User user);
    void secession(String accessToken, String refreshToken, HttpServletRequest request, HttpServletResponse response,
                   User user);
    void setBlackList(String token);
}
