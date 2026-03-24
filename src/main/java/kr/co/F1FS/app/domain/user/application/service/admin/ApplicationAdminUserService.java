package kr.co.F1FS.app.domain.user.application.service.admin;

import kr.co.F1FS.app.domain.user.application.mapper.admin.AdminUserMapper;
import kr.co.F1FS.app.domain.user.application.port.in.QueryUserUseCase;
import kr.co.F1FS.app.domain.user.application.port.in.admin.AdminUserUseCase;
import kr.co.F1FS.app.domain.user.presentation.dto.admin.AdminResponseUserDTO;
import kr.co.F1FS.app.global.presentation.dto.user.SimpleResponseUserDTO;
import kr.co.F1FS.app.global.util.exception.post.PostException;
import kr.co.F1FS.app.global.util.exception.post.PostExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationAdminUserService implements AdminUserUseCase {
    private final QueryUserUseCase queryUserUseCase;
    private final AdminUserMapper adminUserMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<SimpleResponseUserDTO> getUserAll(int page, int size, String condition) {
        Pageable pageable = conditionSwitch(page, size, condition);

        return queryUserUseCase.findAllForSimpleDTO(pageable);
    }

    @Override
    public AdminResponseUserDTO getUserById(Long id) {
        return adminUserMapper.toAdminResponseUserDTO(queryUserUseCase.findById(id));
    }

    @Override
    public Pageable conditionSwitch(int page, int size, String condition) {
        switch (condition){
            case "idASC" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
            case "idDESC" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
            case "nicknameASC" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "nickname"));
            case "nicknameDESC" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "nickname"));
            default:
                throw new PostException(PostExceptionType.CONDITION_ERROR_POST);
        }
    }
}
