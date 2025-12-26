package kr.co.F1FS.app.domain.user.application.service;

import kr.co.F1FS.app.domain.user.application.mapper.UserMapper;
import kr.co.F1FS.app.domain.user.application.port.in.UpdateUserUseCase;
import kr.co.F1FS.app.domain.user.application.port.out.UserJpaPort;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.user.presentation.dto.ModifyDescriptionDTO;
import kr.co.F1FS.app.domain.user.presentation.dto.ModifyNicknameDTO;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDTO;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.global.util.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUserService implements UpdateUserUseCase {
    private final UserJpaPort userJpaPort;
    private final UserDomainService userDomainService;
    private final UserMapper userMapper;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    public void updateLastLoginDate(User user) {
        userDomainService.updateLastLoginDate(user);
        userJpaPort.saveAndFlush(user);
    }

    @Override
    public void updateRole(User user, Role role) {
        cacheEvictUtil.evictCachingUser(user);
        userDomainService.updateRole(user, role);
        userJpaPort.saveAndFlush(user);
    }

    @Override
    public void updatePassword(User user, String password) {
        cacheEvictUtil.evictCachingUser(user);
        userDomainService.updatePassword(user, password);
        userJpaPort.saveAndFlush(user);
    }

    @Override
    public ResponseUserDTO updateNickname(User user, ModifyNicknameDTO dto) {
        cacheEvictUtil.evictCachingUser(user);
        userDomainService.updateNickname(user, dto.getNewNickname());
        userJpaPort.saveAndFlush(user);

        return userMapper.toResponseUserDTO(user);
    }

    @Override
    public ResponseUserDTO updateDescription(User user, ModifyDescriptionDTO dto) {
        cacheEvictUtil.evictCachingUser(user);
        userDomainService.updateDescription(user, dto.getDescription());
        userJpaPort.saveAndFlush(user);

        return userMapper.toResponseUserDTO(user);
    }

    @Override
    public void updateRefreshToken(User user, String refreshToken) {
        cacheEvictUtil.evictCachingUser(user);
        userDomainService.updateRefreshToken(user, refreshToken);
        userJpaPort.saveAndFlush(user);
    }

    @Override
    public void increaseFollow(User followerUser, User followeeUser) {
        userDomainService.increaseFollow(followerUser, followeeUser);

        userJpaPort.saveAndFlush(followerUser);
        userJpaPort.saveAndFlush(followeeUser);
    }

    @Override
    public void decreaseFollow(User followerUser, User followeeUser) {
        userDomainService.decreaseFollow(followerUser, followeeUser);

        userJpaPort.saveAndFlush(followerUser);
        userJpaPort.saveAndFlush(followeeUser);
    }

    @Override
    public void suspendUser(User user, Integer durationDays) {
        cacheEvictUtil.evictCachingUser(user);

        userDomainService.updateSuspendUntil(user, durationDays);
        userDomainService.updateRole(user, Role.DISCIPLINE);
        userDomainService.updateSuspendNum(user);

        userJpaPort.saveAndFlush(user);
    }
}
