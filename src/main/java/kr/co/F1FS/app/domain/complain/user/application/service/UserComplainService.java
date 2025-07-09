package kr.co.F1FS.app.domain.complain.user.application.service;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.domain.complain.user.application.mapper.UserComplainMapper;
import kr.co.F1FS.app.domain.complain.user.application.port.in.UserComplainUseCase;
import kr.co.F1FS.app.domain.complain.user.application.port.out.ComplainUserPort;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.complain.user.domain.UserComplain;
import kr.co.F1FS.app.domain.complain.user.infrastructure.repository.UserComplainRepository;
import kr.co.F1FS.app.domain.complain.user.presentation.dto.CreateUserComplainDTO;
import kr.co.F1FS.app.global.application.service.SlackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserComplainService implements UserComplainUseCase {
    private final ComplainUserPort userPort;
    private final UserComplainMapper complainMapper;
    private final SlackService slackService;
    private final UserComplainRepository complainRepository;

    public void save(UserComplain complain){
        complainRepository.save(complain);
    }

    @Transactional
    public void userComplain(User user, CreateUserComplainDTO dto){
        User toUser = userPort.findByNickname(dto.getToUserNickname());
        UserComplain complain = complainMapper.toUserComplain(dto, toUser, user);

        save(complain);
        sendMessage(complain);
        log.info("유저 신고 접수 완료 : {} -> {}", complain.getFromUser().getNickname(), complain.getToUser().getNickname());
    }

    public Page<UserComplain> findAll(Pageable pageable){
        return complainRepository.findAll(pageable);
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
