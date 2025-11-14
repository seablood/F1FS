package kr.co.F1FS.app.domain.admin.user.application.service;

import kr.co.F1FS.app.domain.admin.user.application.mapper.AdminUserMapper;
import kr.co.F1FS.app.domain.admin.user.application.port.in.AdminUserUseCase;
import kr.co.F1FS.app.domain.admin.user.presentation.dto.AdminResponseUserComplainDTO;
import kr.co.F1FS.app.domain.complain.user.application.port.in.UserComplainUseCase;
import kr.co.F1FS.app.domain.complain.user.domain.UserComplain;
import kr.co.F1FS.app.domain.suspend.application.port.in.SuspensionLogUseCase;
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
    private final UserComplainUseCase complainUseCase;
    private final SuspensionLogUseCase suspensionLogUseCase;
    private final AdminUserMapper adminUserMapper;

    @Override
    public Page<AdminResponseUserComplainDTO> findAll(int page, int size, String condition){
        Pageable pageable = switchCondition(page, size, condition);

        return complainUseCase.findAll(pageable);
    }

    @Override
    public Page<AdminResponseUserComplainDTO> getComplainByUser(int page, int size, String condition, String search){
        Pageable pageable = switchCondition(page, size, condition);
        User toUser = userUseCase.findByUsernameNotDTO(search);
        if(toUser == null) toUser = userUseCase.findByNicknameNotDTO(search);

        return complainUseCase.findAllByToUser(toUser, pageable).map(userComplain -> complainUseCase.toAdminResponseUserComplainDTO(
                userComplain
        ));
    }

    @Override
    @Transactional
    public void setSuspend(SuspendRequestDTO dto){
        User suspendUser = userUseCase.findByNicknameNotDTONotCache(dto.getNickname());
        userUseCase.suspendUser(suspendUser, dto.getDurationDays());

        SuspensionLog log = suspensionLogUseCase.toSuspensionLog(adminUserMapper.toCreateSuspensionLogCommand(dto),
                suspendUser);
        suspensionLogUseCase.save(log);

        List<UserComplain> list = complainUseCase.findAllByToUser(suspendUser);
        if(!list.isEmpty()){
            list.stream().forEach(complain -> complainUseCase.delete(complain));
        }
        userUseCase.saveAndFlush(suspendUser);
    }

    @Override
    public Pageable switchCondition(int page, int size, String condition){
        return switch (condition){
            case "older" ->
                PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));
            default ->
                PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        };
    }
}
