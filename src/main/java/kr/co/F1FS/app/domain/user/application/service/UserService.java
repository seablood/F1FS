package kr.co.F1FS.app.domain.user.application.service;

import kr.co.F1FS.app.domain.admin.auth.presentation.dto.CreateAdminUserDTO;
import kr.co.F1FS.app.domain.user.application.mapper.UserMapper;
import kr.co.F1FS.app.domain.user.application.port.in.UserUseCase;
import kr.co.F1FS.app.domain.user.application.port.out.UserJpaPort;
import kr.co.F1FS.app.domain.user.presentation.dto.CreateUserCommand;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.domain.user.presentation.dto.ModifyNicknameDTO;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserUseCase {
    private final UserMapper userMapper;
    private final CacheEvictUtil cacheEvictUtil;
    private final UserJpaPort userJpaPort;

    @Override
    public User save(User user) {
        return userJpaPort.save(user);
    }

    @Override
    public User saveAndFlush(User user) {
        return userJpaPort.saveAndFlush(user);
    }

    @Override
    public void saveAllAndFlush(List<User> list) {
        userJpaPort.saveAllAndFlush(list);
    }

    @Override
    public User createUser(CreateUserCommand command) {
        return userMapper.toUser(command);
    }

    @Override
    @Cacheable(value = "UserDTO", key = "#nickname", cacheManager = "redisLongCacheManager")
    public ResponseUserDTO findByNickname(String nickname){
        User user = findByNicknameNotDTO(nickname);
        return userMapper.toResponseUserDTO(user);
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
    @Cacheable(value = "User", key = "#nickname", cacheManager = "redisLongCacheManager")
    public User findByNicknameNotDTO(String nickname){
        return userJpaPort.findByNickname(nickname);
    }

    @Override
    public User findByNicknameNotDTONotCache(String nickname) {
        return userJpaPort.findByNickname(nickname);
    }

    @Override
    public User findByUsernameNotDTO(String username){
        return userJpaPort.findByUsernameOrNull(username);
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        return userJpaPort.findByEmailAndPassword(email, password);
    }

    @Override
    public List<User> findAllByLastLoginDateBeforeOrLastLoginDateIsNull(LocalDateTime sixMonthAgo) {
        return userJpaPort.findAllByLastLoginDateBeforeOrLastLoginDateIsNull(sixMonthAgo);
    }

    @Override
    @Transactional
    public ResponseUserDTO modify(User user, ModifyNicknameDTO dto){
        cacheEvictUtil.evictCachingUser(user);
        user.updateNickname(dto.getNewNickname());
        userJpaPort.saveAndFlush(user);
        return userMapper.toResponseUserDTO(user);
    }

    @Override
    public void updateLastLoginDate(User user) {
        user.updateLastLoginDate();
        userJpaPort.saveAndFlush(user);
    }

    @Override
    public void updatePassword(User user, String password) {
        user.updatePassword(password);
        userJpaPort.saveAndFlush(user);
    }

    @Override
    public void updateRefreshToken(User user, String refreshToken) {
        user.updateRefreshToken(refreshToken);
        userJpaPort.saveAndFlush(user);
    }

    @Override
    public void updateRole(User user, Role newRole){
        user.updateRole(newRole);
        userJpaPort.saveAndFlush(user);
    }

    @Override
    public void increaseFollow(User followerUser, User followeeUser) {
        followerUser.changeFollowingNum(1);
        followeeUser.changeFollowerNum(1);

        userJpaPort.saveAndFlush(followerUser);
        userJpaPort.saveAndFlush(followeeUser);
    }

    @Override
    public void decreaseFollow(User followerUser, User followeeUser) {
        followerUser.changeFollowingNum(-1);
        followeeUser.changeFollowerNum(-1);

        userJpaPort.saveAndFlush(followerUser);
        userJpaPort.saveAndFlush(followeeUser);
    }

    @Override
    public User toAdminUser(CreateAdminUserDTO dto) {
        return userMapper.toUser(dto);
    }

    @Override
    public ResponseUserDTO toResponseUserDTO(User user) {
        return userMapper.toResponseUserDTO(user);
    }

    @Override
    public void delete(User user) {
        userJpaPort.delete(user);
    }

    @Override
    public void suspendUser(User user, Integer durationDays){
        user.updateSuspendUntil(durationDays);
        updateRole(user, Role.DISCIPLINE);
        user.updateSuspendNum();

        userJpaPort.saveAndFlush(user);
    }
}
