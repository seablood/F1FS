package kr.co.F1FS.app.domain.user.application.port.in;

import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface QueryUserUseCase {
    User findById(Long id);
    User findByUsername(String username);
    Optional<User> findByUsernameForOptional(String username);
    User findByUsernameOrNull(String username);
    User findByNickname(String nickname);
    ResponseUserDTO findByNicknameForDTO(String nickname);
    User findByEmailAndPassword(String email, String password);
    User findUserByEmail(String email);
    Optional<User> findByRefreshToken(String refreshToken);
    User findByProviderAndProviderIdOrNull(String provider, String providerId);
    List<User> findAllByLastLoginDateBeforeOrLastLoginDateIsNull(LocalDateTime sixMonthAgo);
}
