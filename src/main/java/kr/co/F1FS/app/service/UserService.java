package kr.co.F1FS.app.service;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.dto.CreateUserDTO;
import kr.co.F1FS.app.model.User;
import kr.co.F1FS.app.repository.UserRepository;
import kr.co.F1FS.app.util.ValidationService;
import kr.co.F1FS.app.util.user.UserException;
import kr.co.F1FS.app.util.user.UserExceptionType;
import lombok.RequiredArgsConstructor;
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

    public User findByUsernameNotDTO(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));
    }

    public User findByNicknameNotDTO(String nickname){
        return userRepository.findByNickname(nickname)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND));
    }
}
