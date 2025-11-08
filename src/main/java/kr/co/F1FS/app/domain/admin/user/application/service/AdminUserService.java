package kr.co.F1FS.app.domain.admin.user.application.service;

import kr.co.F1FS.app.domain.admin.user.application.mapper.AdminUserMapper;
import kr.co.F1FS.app.domain.admin.user.application.port.in.AdminUserUseCase;
import kr.co.F1FS.app.domain.admin.user.application.port.out.AdminUserComplainPort;
import kr.co.F1FS.app.domain.admin.user.application.port.out.AdminUserPort;
import kr.co.F1FS.app.domain.admin.user.application.port.out.AdminUserSuspensionLogPort;
import kr.co.F1FS.app.domain.admin.user.presentation.dto.AdminResponseUserComplainDTO;
import kr.co.F1FS.app.domain.complain.user.application.mapper.UserComplainMapper;
import kr.co.F1FS.app.domain.complain.user.application.port.out.UserComplainJpaPort;
import kr.co.F1FS.app.domain.complain.user.domain.UserComplain;
import kr.co.F1FS.app.domain.suspend.application.mapper.SuspensionLogMapper;
import kr.co.F1FS.app.domain.suspend.domain.SuspensionLog;
import kr.co.F1FS.app.domain.user.application.port.in.UserUseCase;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.domain.admin.user.presentation.dto.SuspendRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminUserService implements AdminUserUseCase {
    private final UserUseCase userUseCase;
    private final UserComplainJpaPort complainJpaPort;
    private final AdminUserPort userPort;
    private final AdminUserSuspensionLogPort suspensionLogPort;
    private final AdminUserComplainPort complainPort;
    private final UserComplainMapper complainMapper;
    private final AdminUserMapper adminUserMapper;
    private final SuspensionLogMapper suspensionLogMapper;

    public Page<AdminResponseUserComplainDTO> findAll(int page, int size, String condition){
        Pageable pageable = switchCondition(page, size, condition);

        return complainJpaPort.findAll(pageable);
    }

    public Page<AdminResponseUserComplainDTO> getComplainByUser(int page, int size, String condition, String search){
        Pageable pageable = switchCondition(page, size, condition);
        User toUser = userUseCase.findByUsernameNotDTO(search);
        if(toUser == null) toUser = userUseCase.findByNicknameNotDTO(search);

        return complainPort.getComplainByUser(toUser, pageable).map(userComplain -> complainMapper.toAdminResponseUserComplainDTO(
                userComplain
        ));
    }

    @Transactional
    public void setSuspend(SuspendRequestDTO dto){
        User suspendUser = userPort.findByNicknameNotDTO(dto.getNickname());
        userUseCase.suspendUser(suspendUser, dto.getDurationDays());

        SuspensionLog log = suspensionLogMapper.toSuspensionLog(adminUserMapper.toCreateSuspensionLogCommand(dto),
                suspendUser);
        suspensionLogPort.save(log);

        List<UserComplain> list = complainPort.getComplainByUser(suspendUser);
        if(!list.isEmpty()){
            list.stream().forEach(complain -> complainPort.delete(complain));
        }
        userPort.saveAndFlush(suspendUser);
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
