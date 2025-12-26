package kr.co.F1FS.app.domain.user.application.service;

import kr.co.F1FS.app.domain.user.application.mapper.UserMapper;
import kr.co.F1FS.app.domain.user.application.port.in.QueryUserUseCase;
import kr.co.F1FS.app.domain.user.application.port.out.UserJpaPort;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QueryUserService implements QueryUserUseCase {
    private final UserJpaPort userJpaPort;
    private final UserMapper userMapper;

    @Override
    public User findById(Long id) {
        return userJpaPort.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userJpaPort.findByUsername(username);
    }

    @Override
    public Optional<User> findByUsernameForOptional(String username) {
        return userJpaPort.findByUsernameForOptional(username);
    }

    @Override
    public User findByUsernameOrNull(String username) {
        return userJpaPort.findByUsernameOrNull(username);
    }

    @Override
    public User findByNickname(String nickname) {
        return userJpaPort.findByNickname(nickname);
    }

    @Override
    public ResponseUserDTO findByNicknameForDTO(String nickname) {
        return userMapper.toResponseUserDTO(userJpaPort.findByNickname(nickname));
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        return userJpaPort.findByEmailAndPassword(email, password);
    }

    @Override
    public User findUserByEmail(String email) {
        return userJpaPort.findUserByEmail(email);
    }

    @Override
    public Optional<User> findByRefreshToken(String refreshToken) {
        return userJpaPort.findByRefreshToken(refreshToken);
    }

    @Override
    public User findByProviderAndProviderIdOrNull(String provider, String providerId) {
        return userJpaPort.findByProviderAndProviderIdOrNull(provider, providerId);
    }

    @Override
    public List<User> findAllByLastLoginDateBeforeOrLastLoginDateIsNull(LocalDateTime sixMonthAgo) {
        return userJpaPort.findAllByLastLoginDateBeforeOrLastLoginDateIsNull(sixMonthAgo);
    }
}
