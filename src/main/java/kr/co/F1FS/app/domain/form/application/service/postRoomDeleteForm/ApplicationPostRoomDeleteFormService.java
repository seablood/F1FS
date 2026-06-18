package kr.co.F1FS.app.domain.form.application.service.postRoomDeleteForm;

import kr.co.F1FS.app.domain.form.application.mapper.FormMapper;
import kr.co.F1FS.app.domain.form.application.port.in.postRoomDeleteForm.*;
import kr.co.F1FS.app.domain.form.domain.PostRoomDeleteForm;
import kr.co.F1FS.app.domain.form.presentation.dto.CreatePostRoomDeleteFormDTO;
import kr.co.F1FS.app.domain.form.presentation.dto.ModifyPostRoomDeleteFormDTO;
import kr.co.F1FS.app.domain.form.presentation.dto.ResponsePostRoomDeleteFormListDTO;
import kr.co.F1FS.app.domain.notification.application.port.in.push.FCMLiveUseCase;
import kr.co.F1FS.app.domain.postRoom.application.port.in.QueryPostRoomUseCase;
import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.form.ResponsePostRoomDeleteFormDTO;
import kr.co.F1FS.app.global.util.AuthorCertification;
import kr.co.F1FS.app.global.util.exception.form.PostRoomDeleteFormException;
import kr.co.F1FS.app.global.util.exception.form.PostRoomDeleteFormExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ApplicationPostRoomDeleteFormService implements PostRoomDeleteFormUseCase {
    private final CreatePostRoomDeleteFormUseCase createPostRoomDeleteFormUseCase;
    private final UpdatePostRoomDeleteFormUseCase updatePostRoomDeleteFormUseCase;
    private final DeletePostRoomDeleteFormUseCase deletePostRoomDeleteFormUseCase;
    private final QueryPostRoomDeleteFormUseCase queryPostRoomDeleteFormUseCase;
    private final CheckPostRoomDeleteFormUseCase checkPostRoomDeleteFormUseCase;
    private final QueryPostRoomUseCase queryPostRoomUseCase;
    private final FCMLiveUseCase fcmLiveUseCase;
    private final FormMapper formMapper;

    @Override
    @Transactional
    public ResponsePostRoomDeleteFormDTO save(CreatePostRoomDeleteFormDTO dto, User user, Long roomId) {
        if(checkPostRoomDeleteFormUseCase.existsByPostRoom(roomId)){
            throw new PostRoomDeleteFormException(PostRoomDeleteFormExceptionType.ALREADY_HAS_FORM);
        }
        PostRoom postRoom = queryPostRoomUseCase.findByIdWithJoin(roomId);
        if(!AuthorCertification.certification(user.getUsername(), postRoom.getMasterUser().getUsername())){
            throw new PostRoomDeleteFormException(PostRoomDeleteFormExceptionType.NOT_AUTHORITY_CREATE_DELETE_FORM);
        }
        PostRoomDeleteForm deleteForm = createPostRoomDeleteFormUseCase.save(dto, user, postRoom);
        fcmLiveUseCase.sendPushAfterPostRoomDeleteFormSave(user, deleteForm, deleteForm.getId());

        return formMapper.toResponsePostRoomDeleteFormDTO(deleteForm);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponsePostRoomDeleteFormListDTO> getPostRoomDeleteFormListByUser(int page, int size, String condition, User user) {
        Pageable pageable = conditionSwitch(page, size, condition);

        return queryPostRoomDeleteFormUseCase.findAllByUserForDTO(user.getId(), pageable);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "PostRoomDeleteFormDTO", key = "#formId", cacheManager = "redisLongCacheManager")
    public ResponsePostRoomDeleteFormDTO getPostRoomDeleteFormById(Long formId) {
        return queryPostRoomDeleteFormUseCase.findByIdWithJoinForDTO(formId);
    }

    @Override
    public ResponsePostRoomDeleteFormDTO modify(ModifyPostRoomDeleteFormDTO dto, Long formId, User user) {
        PostRoomDeleteForm deleteForm = queryPostRoomDeleteFormUseCase.findByIdWithJoin(formId);

        updatePostRoomDeleteFormUseCase.modify(dto, deleteForm, user);
        return formMapper.toResponsePostRoomDeleteFormDTO(deleteForm);
    }

    @Override
    @Transactional
    public void delete(Long formId, User user) {
        PostRoomDeleteForm deleteForm = queryPostRoomDeleteFormUseCase.findByIdWithJoin(formId);

        deletePostRoomDeleteFormUseCase.delete(deleteForm, user);
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
