package kr.co.F1FS.app.domain.suspend.application.service.admin;

import kr.co.F1FS.app.domain.complain.user.application.port.in.DeleteUserComplainUseCase;
import kr.co.F1FS.app.domain.complain.user.application.port.in.QueryUserComplainUseCase;
import kr.co.F1FS.app.domain.complain.user.domain.UserComplain;
import kr.co.F1FS.app.domain.suspend.application.mapper.admin.AdminSuspensionLogMapper;
import kr.co.F1FS.app.domain.suspend.application.port.in.CreateSuspensionLogUseCase;
import kr.co.F1FS.app.domain.suspend.application.port.in.admin.AdminSuspensionLogUseCase;
import kr.co.F1FS.app.domain.suspend.presentation.dto.admin.SuspendRequestDTO;
import kr.co.F1FS.app.domain.user.application.port.in.QueryUserUseCase;
import kr.co.F1FS.app.domain.user.application.port.in.UpdateUserUseCase;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationAdminSuspensionLogService implements AdminSuspensionLogUseCase {
    private final CreateSuspensionLogUseCase createSuspensionLogUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final QueryUserUseCase queryUserUseCase;
    private final DeleteUserComplainUseCase deleteUserComplainUseCase;
    private final QueryUserComplainUseCase queryUserComplainUseCase;
    private final AdminSuspensionLogMapper adminSuspensionLogMapper;

    @Override
    public void setSuspend(SuspendRequestDTO dto) {
        User suspendUser = queryUserUseCase.findByNickname(dto.getNickname());
        updateUserUseCase.suspendUser(suspendUser, dto.getDurationDays());

        createSuspensionLogUseCase.save(adminSuspensionLogMapper.toCreateSuspensionLogCommand(dto), suspendUser);

        List<UserComplain> list = queryUserComplainUseCase.findAllByToUser(suspendUser);
        if(!list.isEmpty()){
            list.stream().forEach(complain -> deleteUserComplainUseCase.delete(complain));
        }
    }
}
