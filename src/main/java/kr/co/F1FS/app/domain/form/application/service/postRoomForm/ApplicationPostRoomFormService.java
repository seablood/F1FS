package kr.co.F1FS.app.domain.form.application.service.postRoomForm;

import kr.co.F1FS.app.domain.form.application.mapper.FormMapper;
import kr.co.F1FS.app.domain.form.application.port.in.postRoomForm.*;
import kr.co.F1FS.app.domain.form.domain.PostRoomForm;
import kr.co.F1FS.app.domain.form.presentation.dto.CreatePostRoomFormDTO;
import kr.co.F1FS.app.domain.form.presentation.dto.ModifyPostRoomFormDTO;
import kr.co.F1FS.app.domain.form.presentation.dto.ResponsePostRoomFormListDTO;
import kr.co.F1FS.app.domain.notification.application.port.in.push.FCMLiveUseCase;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.form.ResponsePostRoomFormDTO;
import kr.co.F1FS.app.global.util.exception.form.PostRoomFormException;
import kr.co.F1FS.app.global.util.exception.form.PostRoomFormExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationPostRoomFormService implements PostRoomFormUseCase {
    private final CreatePostRoomFormUseCase createPostRoomFormUseCase;
    private final UpdatePostRoomFormUseCase updatePostRoomFormUseCase;
    private final DeletePostRoomFormUseCase deletePostRoomFormUseCase;
    private final QueryPostRoomFormUseCase queryPostRoomFormUseCase;
    private final CheckPostRoomFormUseCase checkPostRoomFormUseCase;
    private final FCMLiveUseCase fcmLiveUseCase;
    private final FormMapper formMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public ResponsePostRoomFormDTO save(CreatePostRoomFormDTO dto, User user) {
        if(checkPostRoomFormUseCase.existsByUser(user.getId())){
            throw new PostRoomFormException(PostRoomFormExceptionType.ALREADY_HAS_FORM);
        }
        if(dto.getPassword() != null) dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        PostRoomForm postRoomForm = createPostRoomFormUseCase.save(dto, user);
        fcmLiveUseCase.sendPushAfterPostRoomSave(user, postRoomForm, postRoomForm.getId());

        return formMapper.toResponsePostRoomFormDTO(postRoomForm);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponsePostRoomFormListDTO> getPostRoomFormByUser(int page, int size, String condition, User user) {
        Pageable pageable = conditionSwitch(page, size, condition);

        return queryPostRoomFormUseCase.findAllByUserForDTO(user.getId(), pageable);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "PostRoomFormDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponsePostRoomFormDTO getPostRoomFormById(Long id) {
        return queryPostRoomFormUseCase.findByIdWithJoinForDTO(id);
    }

    @Override
    @Transactional
    public ResponsePostRoomFormDTO modify(ModifyPostRoomFormDTO dto, Long formId, User user) {
        if(dto.getPassword() != null) dto.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        PostRoomForm postRoomForm = queryPostRoomFormUseCase.findByIdWithJoin(formId);

        updatePostRoomFormUseCase.modify(dto, postRoomForm, user);
        return formMapper.toResponsePostRoomFormDTO(postRoomForm);
    }

    @Override
    @Transactional
    public void delete(Long formId, User user) {
        PostRoomForm postRoomForm = queryPostRoomFormUseCase.findByIdWithJoin(formId);

        deletePostRoomFormUseCase.delete(postRoomForm, user);
    }

    public Pageable conditionSwitch(int page, int size, String condition){
        switch (condition){
            case "new" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
            case "older" :
                return PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "createdAt"));
            default:
                return PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        }
    }
}
