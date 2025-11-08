package kr.co.F1FS.app.domain.auth.application.port.in;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.co.F1FS.app.domain.auth.domain.VerificationCode;
import kr.co.F1FS.app.domain.auth.presentation.dto.AuthorizationUserDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.auth.presentation.dto.CreateUserDTO;
import kr.co.F1FS.app.domain.auth.presentation.dto.ModifyPasswordDTO;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDTO;

import java.util.List;

public interface AuthUseCase {
    ResponseUserDTO save(CreateUserDTO userDTO);
    List<VerificationCode> findAll();
    void sendEmail(User user, String option);
    void sendEmail(AuthorizationUserDTO dto, String option);
    VerificationCode createVerificationCode(User user);
    String generateVerificationCode();
    void verifyCode(String email, String code);
    void updatePassword(User user, ModifyPasswordDTO passwordDTO);
    void logout(String accessToken, String refreshToken, HttpServletRequest request, HttpServletResponse response,
                User user);
    void secession(String accessToken, String refreshToken, HttpServletRequest request, HttpServletResponse response,
                   User user);
    void setBlackList(String token);
    void delete(VerificationCode code);
}
