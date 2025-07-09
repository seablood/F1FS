package kr.co.F1FS.app.domain.user.application.service;

import kr.co.F1FS.app.domain.user.application.mapper.UserMapper;
import kr.co.F1FS.app.domain.user.application.port.in.UserUseCase;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.domain.user.presentation.dto.ModifyNicknameDTO;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.user.infrastructure.repository.UserRepository;
import kr.co.F1FS.app.global.util.Role;
import kr.co.F1FS.app.global.util.exception.user.UserException;
import kr.co.F1FS.app.global.util.exception.user.UserExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserUseCase {
    private final UserMapper userMapper;
    private final CacheEvictUtil cacheEvictUtil;
    private final UserRepository userRepository;

    @Cacheable(value = "UserDTO", key = "#nickname", cacheManager = "redisLongCacheManager")
    public ResponseUserDTO findByNickname(String nickname){
        User user = findByNicknameNotDTO(nickname);
        return userMapper.toResponseUserDTO(user);
    }

    @Cacheable(value = "User", key = "#nickname", cacheManager = "redisLongCacheManager")
    public User findByNicknameNotDTO(String nickname){
        return userRepository.findByNickname(nickname)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));
    }

    public User findByUsernameNotDTO(String username){
        return userRepository.findByUsername(username)
                .orElse(null);
    }

    @Transactional
    public ResponseUserDTO modify(User user, ModifyNicknameDTO dto){
        cacheEvictUtil.evictCachingUser(user);
        user.updateNickname(dto.getNewNickname());
        userRepository.saveAndFlush(user);
        return userMapper.toResponseUserDTO(user);
    }

    public void updateLastLoginDate(User user) {
        user.updateLastLoginDate();
    }

    public void updatePassword(User user, String password) {
        user.updatePassword(password);
    }

    public void updateRefreshToken(User user, String refreshToken) {
        user.updateRefreshToken(refreshToken);
    }

    public void updateRole(User user, Role newRole){
        user.updateRole(newRole);
    }

    public void increaseFollow(User followerUser, User followeeUser) {
        followerUser.changeFollowingNum(1);
        followeeUser.changeFollowerNum(1);
    }

    public void decreaseFollow(User followerUser, User followeeUser) {
        followerUser.changeFollowingNum(-1);
        followeeUser.changeFollowerNum(-1);
    }

    public void suspendUser(User user, Integer durationDays){
        user.updateSuspendUntil(durationDays);
        updateRole(user, Role.DISCIPLINE);
        user.updateSuspendNum();
    }
}
