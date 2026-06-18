package kr.co.F1FS.app.domain.form.application.service.postRoomDeleteForm;

import kr.co.F1FS.app.domain.form.application.port.in.postRoomDeleteForm.UpdatePostRoomDeleteFormUseCase;
import kr.co.F1FS.app.domain.form.application.port.out.postRoomDeleteForm.PostRoomDeleteFormJpaPort;
import kr.co.F1FS.app.domain.form.domain.PostRoomDeleteForm;
import kr.co.F1FS.app.domain.form.presentation.dto.ModifyPostRoomDeleteFormDTO;
import kr.co.F1FS.app.domain.form.presentation.dto.admin.UpdatePostRoomDeleteFormDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.global.util.exception.form.PostRoomDeleteFormException;
import kr.co.F1FS.app.global.util.exception.form.PostRoomDeleteFormExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdatePostRoomDeleteFormService implements UpdatePostRoomDeleteFormUseCase {
    private final PostRoomDeleteFormJpaPort postRoomDeleteFormJpaPort;
    private final PostRoomDeleteFormDomainService postRoomDeleteFormDomainService;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    public void updateConfirm(UpdatePostRoomDeleteFormDTO dto, PostRoomDeleteForm deleteForm) {
        cacheEvictUtil.evictCachingPostRoomDeleteForm(deleteForm);

        postRoomDeleteFormDomainService.confirm(dto, deleteForm);
        postRoomDeleteFormJpaPort.saveAndFlush(deleteForm);
    }

    @Override
    public void modify(ModifyPostRoomDeleteFormDTO dto, PostRoomDeleteForm deleteForm, User user) {
        cacheEvictUtil.evictCachingPostRoomDeleteForm(deleteForm);

        if(!postRoomDeleteFormDomainService.certification(user, deleteForm)){
            throw new PostRoomDeleteFormException(PostRoomDeleteFormExceptionType.NOT_AUTHORITY_UPDATE_POST_ROOM_DELETE_FORM);
        }

        postRoomDeleteFormDomainService.modify(dto, deleteForm);
        postRoomDeleteFormJpaPort.saveAndFlush(deleteForm);
    }
}
