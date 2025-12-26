package kr.co.F1FS.app.domain.complain.user.application.service.admin;

import kr.co.F1FS.app.domain.complain.user.presentation.dto.admin.AdminResponseUserComplainDTO;
import kr.co.F1FS.app.domain.complain.user.application.mapper.admin.AdminUserComplainMapper;
import kr.co.F1FS.app.domain.complain.user.application.port.in.admin.AdminUserComplainUseCase;
import kr.co.F1FS.app.domain.complain.user.application.port.in.QueryUserComplainUseCase;
import kr.co.F1FS.app.domain.user.application.port.in.QueryUserUseCase;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.complain.ResponseUserComplainDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationAdminUserComplainService implements AdminUserComplainUseCase {
    private final QueryUserComplainUseCase queryUserComplainUseCase;
    private final QueryUserUseCase queryUserUseCase;
    private final AdminUserComplainMapper adminUserComplainMapper;

    @Override
    public Page<AdminResponseUserComplainDTO> findAll(int page, int size, String condition) {
        Pageable pageable = switchCondition(page, size, condition);

        return queryUserComplainUseCase.findAll(pageable)
                .map(userComplain -> adminUserComplainMapper.toAdminResponseUserComplainDTO(userComplain));
    }

    @Override
    public Page<AdminResponseUserComplainDTO> getComplainByUser(int page, int size, String condition, String search) {
        Pageable pageable = switchCondition(page, size, condition);
        User toUser = queryUserUseCase.findByUsernameOrNull(search);
        if(toUser == null) toUser = queryUserUseCase.findByNickname(search);

        return queryUserComplainUseCase.findAllByToUser(toUser, pageable)
                .map(userComplain -> adminUserComplainMapper.toAdminResponseUserComplainDTO(userComplain));
    }

    @Override
    @Cacheable(value = "UserComplainDTOForAdmin", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponseUserComplainDTO getComplainById(Long id) {
        return queryUserComplainUseCase.findByIdForDTO(id);
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
}
