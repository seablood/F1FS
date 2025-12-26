package kr.co.F1FS.app.domain.complain.user.application.service;

import kr.co.F1FS.app.domain.complain.user.application.port.in.CreateUserComplainUseCase;
import kr.co.F1FS.app.domain.complain.user.application.port.in.DeleteUserComplainUseCase;
import kr.co.F1FS.app.domain.complain.user.application.port.in.QueryUserComplainUseCase;
import kr.co.F1FS.app.domain.complain.user.application.port.in.UserComplainUseCase;
import kr.co.F1FS.app.domain.user.application.port.in.QueryUserUseCase;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.complain.user.domain.UserComplain;
import kr.co.F1FS.app.domain.complain.user.presentation.dto.CreateUserComplainDTO;
import kr.co.F1FS.app.global.application.service.SlackService;
import kr.co.F1FS.app.global.presentation.dto.complain.ResponseUserComplainDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationUserComplainService implements UserComplainUseCase {
    private final CreateUserComplainUseCase createUserComplainUseCase;
    private final DeleteUserComplainUseCase deleteUserComplainUseCase;
    private final QueryUserComplainUseCase queryUserComplainUseCase;
    private final QueryUserUseCase queryUserUseCase;
    private final SlackService slackService;

    @Override
    @Transactional
    public void userComplain(User user, CreateUserComplainDTO dto){
        User toUser = queryUserUseCase.findByNickname(dto.getToUserNickname());
        UserComplain complain = createUserComplainUseCase.save(dto, toUser, user);

        sendMessage(complain);
        log.info("유저 신고 접수 완료 : {} -> {}", complain.getFromUser().getNickname(), complain.getToUser().getNickname());
    }

    @Override
    @Cacheable(value = "UserComplainDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponseUserComplainDTO getUserComplain(Long id) {
        return queryUserComplainUseCase.findByIdForDTO(id);
    }

    @Override
    public Page<ResponseUserComplainDTO> getUserComplainListByFromUser(int page, int size, String condition, User fromUser) {
        Pageable pageable = switchCondition(page, size, condition);
        return queryUserComplainUseCase.findAllByFromUserForDTO(fromUser, pageable);
    }

    @Override
    @Transactional
    public void delete(Long id, User user) {
        UserComplain userComplain = queryUserComplainUseCase.findById(id);

        deleteUserComplainUseCase.delete(userComplain, user);
    }

    @Override
    public Pageable switchCondition(int page, int size, String condition) {
        return switch (condition){
            case "older" ->
                    PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));
            default ->
                    PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        };
    }

    public void sendMessage(UserComplain complain){
        String title = "사용자 신고가 접수되었습니다.";
        HashMap<String, String> data = new HashMap<>();
        data.put("신고자", complain.getFromUser().getNickname());
        data.put("신고된 유저", complain.getToUser().getNickname());
        data.put("신고 사유", complain.getDescription());

        slackService.sendComplainMessage(title, data);
    }
}
