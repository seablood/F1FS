package kr.co.F1FS.app.service;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.dto.CreateUserDTO;
import kr.co.F1FS.app.dto.ResponseUserDTO;
import kr.co.F1FS.app.model.User;
import kr.co.F1FS.app.repository.UserRepository;
import kr.co.F1FS.app.util.ValidationService;
import kr.co.F1FS.app.util.user.UserException;
import kr.co.F1FS.app.util.user.UserExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ValidationService validationService;

    @Transactional
    public User save(CreateUserDTO userDTO){
        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        User user = CreateUserDTO.toEntity(userDTO);
        validationService.checkValid(user);

        return userRepository.save(user);
    }

    @Cacheable(value = "UserDTO", key = "#nickname", cacheManager = "redisLongCacheManager")
    public ResponseUserDTO findByNickname(String nickname){
        User user = findByNicknameNotDTO(nickname);
        return ResponseUserDTO.toDto(user);
    }

    public User findByNicknameNotDTO(String nickname){
        return userRepository.findByNickname(nickname)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));
    }
}
