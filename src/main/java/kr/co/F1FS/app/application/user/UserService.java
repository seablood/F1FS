package kr.co.F1FS.app.application.user;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.application.SlackService;
import kr.co.F1FS.app.domain.model.rdb.UserComplain;
import kr.co.F1FS.app.domain.repository.rdb.user.UserComplainRepository;
import kr.co.F1FS.app.presentation.user.dto.CreateUserComplainDTO;
import kr.co.F1FS.app.presentation.user.dto.ResponseUserDTO;
import kr.co.F1FS.app.domain.model.rdb.User;
import kr.co.F1FS.app.domain.repository.rdb.user.UserRepository;
import kr.co.F1FS.app.global.util.exception.user.UserException;
import kr.co.F1FS.app.global.util.exception.user.UserExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserComplainRepository complainRepository;
    private final SlackService slackService;

    @Transactional
    public void userComplain(User user, CreateUserComplainDTO dto){
        User toUser = findByNicknameNotDTO(dto.getToUserNickname());
        UserComplain complain = UserComplain.builder()
                .toUser(toUser)
                .fromUser(user)
                .description(dto.getDescription())
                .paraphrase(dto.getParaphrase())
                .build();

        complainRepository.save(complain);
        sendMessage(complain, dto.getDescription());
        log.info("유저 신고 접수 완료 : {} -> {}", complain.getFromUser().getNickname(), complain.getToUser().getNickname());
    }

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

    public void sendMessage(UserComplain complain, String description){
        String title = "사용자 신고가 접수되었습니다.";
        HashMap<String, String> data = new HashMap<>();
        data.put("신고자", complain.getFromUser().getNickname());
        data.put("신고된 유저", complain.getToUser().getNickname());
        data.put("신고 사유", description);

        slackService.sendUserComplainMessage(title, data);
    }
}
