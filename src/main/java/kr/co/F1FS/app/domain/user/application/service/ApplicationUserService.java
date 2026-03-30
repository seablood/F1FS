package kr.co.F1FS.app.domain.user.application.service;

import kr.co.F1FS.app.domain.elastic.application.port.in.user.QueryUserSearchUseCase;
import kr.co.F1FS.app.domain.elastic.application.port.in.user.UpdateUserSearchUseCase;
import kr.co.F1FS.app.domain.elastic.domain.UserDocument;
import kr.co.F1FS.app.domain.user.application.mapper.UserMapper;
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
    private final UpdateUserSearchUseCase updateUserSearchUseCase;
    private final QueryUserSearchUseCase queryUserSearchUseCase;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "UserDTO", key = "#nickname", cacheManager = "redisLongCacheManager")
    public ResponseUserDTO getUserByNickname(String nickname){
        return queryUserUseCase.findByNicknameForDTO(nickname);
    }

    @Override
    @Transactional
    public ResponseUserDTO modifyNickname(User user, ModifyNicknameDTO dto){
        UserDocument userDocument = queryUserSearchUseCase.findById(user.getId());

        updateUserUseCase.updateNickname(user, dto);
        updateUserSearchUseCase.modify(userDocument, user);

        return userMapper.toResponseUserDTO(user);
    }

    @Override
    @Transactional
    public ResponseUserDTO modifyDescription(User user, ModifyDescriptionDTO dto) {
        return updateUserUseCase.updateDescription(user, dto);
    }
}
