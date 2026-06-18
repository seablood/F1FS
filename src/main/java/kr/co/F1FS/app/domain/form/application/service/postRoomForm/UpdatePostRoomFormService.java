package kr.co.F1FS.app.domain.form.application.service.postRoomForm;

import kr.co.F1FS.app.domain.form.application.port.in.postRoomForm.UpdatePostRoomFormUseCase;
import kr.co.F1FS.app.domain.form.application.port.out.postRoomForm.PostRoomFormJpaPort;
import kr.co.F1FS.app.domain.form.domain.PostRoomForm;
import kr.co.F1FS.app.domain.form.presentation.dto.ModifyPostRoomFormDTO;
import kr.co.F1FS.app.domain.form.presentation.dto.admin.UpdatePostRoomFormDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.global.util.exception.form.PostRoomFormException;
import kr.co.F1FS.app.global.util.exception.form.PostRoomFormExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdatePostRoomFormService implements UpdatePostRoomFormUseCase {
    private final PostRoomFormJpaPort postRoomFormJpaPort;
    private final PostRoomFormDomainService postRoomFormDomainService;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    public void updateConfirm(UpdatePostRoomFormDTO dto, PostRoomForm postRoomForm) {
        cacheEvictUtil.evictCachingPostRoomForm(postRoomForm);

        postRoomFormDomainService.confirm(dto, postRoomForm);
        postRoomFormJpaPort.saveAndFlush(postRoomForm);
    }

    @Override
    public void modify(ModifyPostRoomFormDTO dto, PostRoomForm postRoomForm, User user) {
        cacheEvictUtil.evictCachingPostRoomForm(postRoomForm);

        if(!postRoomFormDomainService.certification(user, postRoomForm)){
            throw new PostRoomFormException(PostRoomFormExceptionType.NOT_AUTHORITY_UPDATE_POST_ROOM_FORM);
        }

        postRoomFormDomainService.modify(dto, postRoomForm);
        postRoomFormJpaPort.saveAndFlush(postRoomForm);
    }
}
