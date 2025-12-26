package kr.co.F1FS.app.domain.user.application.service;

import kr.co.F1FS.app.domain.user.application.port.in.QueryUserUseCase;
import kr.co.F1FS.app.domain.user.application.port.in.UpdateUserUseCase;
import kr.co.F1FS.app.domain.user.application.port.in.UserUseCase;
import kr.co.F1FS.app.domain.user.presentation.dto.ModifyDescriptionDTO;
import kr.co.F1FS.app.domain.user.presentation.dto.ModifyNicknameDTO;
import kr.co.F1FS.app.global.presentation.dto.user.ResponseUserDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationUserService implements UserUseCase {
    private final UpdateUserUseCase updateUserUseCase;
    private final QueryUserUseCase queryUserUseCase;

    @Override
    @Cacheable(value = "UserDTO", key = "#nickname", cacheManager = "redisLongCacheManager")
    public ResponseUserDTO findByNickname(String nickname){
        return queryUserUseCase.findByNicknameForDTO(nickname);
    }

    @Override
    @Transactional
    public ResponseUserDTO modifyNickname(User user, ModifyNicknameDTO dto){
        return updateUserUseCase.updateNickname(user, dto);
    }

    @Override
    @Transactional
    public ResponseUserDTO modifyDescription(User user, ModifyDescriptionDTO dto) {
        return updateUserUseCase.updateDescription(user, dto);
    }
}
