package kr.co.F1FS.app.application.user;

import kr.co.F1FS.app.presentation.user.dto.ResponseUserDTO;
import kr.co.F1FS.app.domain.model.rdb.User;
import kr.co.F1FS.app.domain.repository.rdb.user.UserRepository;
import kr.co.F1FS.app.global.util.exception.user.UserException;
import kr.co.F1FS.app.global.util.exception.user.UserExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Cacheable(value = "UserDTO", key = "#nickname", cacheManager = "redisLongCacheManager")
    public ResponseUserDTO findByNickname(String nickname){
        User user = findByNicknameNotDTO(nickname);
        return ResponseUserDTO.toDto(user);
    }

    @Cacheable(value = "User", key = "#nickname", cacheManager = "redisLongCacheManager")
    public User findByNicknameNotDTO(String nickname){
        return userRepository.findByNickname(nickname)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));
    }
}
