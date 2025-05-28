package kr.co.F1FS.app.application.admin.user;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.application.complain.user.UserComplainService;
import kr.co.F1FS.app.application.suspend.SuspensionLogService;
import kr.co.F1FS.app.application.user.UserService;
import kr.co.F1FS.app.domain.model.rdb.SuspensionLog;
import kr.co.F1FS.app.domain.model.rdb.User;
import kr.co.F1FS.app.domain.repository.rdb.user.UserRepository;
import kr.co.F1FS.app.global.util.Role;
import kr.co.F1FS.app.presentation.admin.user.dto.ResponseUserComplainDTO;
import kr.co.F1FS.app.presentation.admin.user.dto.SuspendRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminUserService {
    private final UserService userService;
    private final SuspensionLogService suspensionLogService;
    private final UserComplainService complainService;
    private final UserRepository userRepository;

    public Page<ResponseUserComplainDTO> findAll(int page, int size, String condition){
        Pageable pageable = switchCondition(page, size, condition);

        return complainService.findAll(pageable);
    }

    public Page<ResponseUserComplainDTO> getComplainByUser(int page, int size, String condition, String search){
        Pageable pageable = switchCondition(page, size, condition);
        User toUser = userService.findByUsernameNotDTO(search);
        if(toUser == null) toUser = userService.findByNicknameNotDTO(search);

        return complainService.getComplainByUser(toUser, pageable);
    }

    @Transactional
    public void setSuspend(SuspendRequestDTO dto){
        User suspendUser = userService.findByNicknameNotDTO(dto.getNickname());
        suspendUser.setSuspendUntil(dto.getDurationDays());
        suspendUser.updateRole(Role.DISCIPLINE);

        SuspensionLog log = SuspensionLog.builder()
                        .suspendUser(suspendUser)
                        .durationDays(dto.getDurationDays())
                        .suspendUntil(suspendUser.getSuspendUntil())
                        .description(dto.getDescription())
                        .paraphrase(dto.getParaphrase())
                        .build();
        suspensionLogService.save(log);

        complainService.deleteComplainByUser(suspendUser);
        userRepository.saveAndFlush(suspendUser);
    }

    public Pageable switchCondition(int page, int size, String condition){
        return switch (condition){
            case "older" ->
                PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));
            default ->
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        };
    }
}
