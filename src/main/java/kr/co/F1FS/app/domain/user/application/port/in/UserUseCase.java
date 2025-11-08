package kr.co.F1FS.app.domain.user.application.port.in;

import kr.co.F1FS.app.domain.admin.auth.presentation.dto.CreateAdminUserDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.user.presentation.dto.CreateUserCommand;
import kr.co.F1FS.app.domain.user.presentation.dto.ModifyNicknameDTO;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDTO;
import kr.co.F1FS.app.global.util.Role;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserUseCase {
    User save(User user);
    User saveAndFlush(User user);
    void saveAllAndFlush(List<User> list);
    User createUser(CreateUserCommand command);
    ResponseUserDTO findByNickname(String nickname);
    User findUserByEmail(String email);
    Optional<User> findByRefreshToken(String refreshToken);
    User findByNicknameNotDTO(String nickname);
    User findByNicknameNotDTONotCache(String nickname);
    User findByUsernameNotDTO(String username);
    User findByEmailAndPassword(String email, String password);
    List<User> findAllByLastLoginDateBeforeOrLastLoginDateIsNull(LocalDateTime sixMonthAgo);
    ResponseUserDTO modify(User user, ModifyNicknameDTO dto);
    void updateRole(User user, Role newRole);
    void suspendUser(User user, Integer durationDays);
    void updateLastLoginDate(User user);
    void updatePassword(User user, String password);
    void updateRefreshToken(User user, String refreshToken);
    void increaseFollow(User followerUser, User followeeUser);
    void decreaseFollow(User followerUser, User followeeUser);
    User toAdminUser(CreateAdminUserDTO dto);
    ResponseUserDTO toResponseUserDTO(User user);
    void delete(User user);
}
