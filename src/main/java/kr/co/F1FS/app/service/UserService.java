package kr.co.F1FS.app.service;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.dto.CreateUserDTO;
import kr.co.F1FS.app.model.User;
import kr.co.F1FS.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public User save(CreateUserDTO userDTO){
        userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        User user = CreateUserDTO.toEntity(userDTO);

        return userRepository.save(user);
    }
}
