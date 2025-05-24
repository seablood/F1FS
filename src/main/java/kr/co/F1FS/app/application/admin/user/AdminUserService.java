package kr.co.F1FS.app.application.admin.user;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.application.user.UserService;
import kr.co.F1FS.app.domain.model.rdb.SuspensionLog;
import kr.co.F1FS.app.domain.model.rdb.User;
import kr.co.F1FS.app.domain.repository.rdb.suspend.SuspensionLogRepository;
import kr.co.F1FS.app.domain.repository.rdb.user.UserComplainRepository;
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
    private final UserRepository userRepository;
    private final SuspensionLogRepository suspensionLogRepository;
    private final UserComplainRepository complainRepository;

    public Page<ResponseUserComplainDTO> findAll(int page, int size, String condition){
        Pageable pageable = switchCondition(page, size, condition);

        return complainRepository.findAll(pageable).map(complain -> ResponseUserComplainDTO.toDto(complain));
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
        suspensionLogRepository.save(log);

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
