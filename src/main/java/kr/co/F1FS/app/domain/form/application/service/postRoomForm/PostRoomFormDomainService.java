package kr.co.F1FS.app.domain.form.application.service.postRoomForm;

import kr.co.F1FS.app.domain.form.application.mapper.FormMapper;
import kr.co.F1FS.app.domain.form.domain.PostRoomForm;
import kr.co.F1FS.app.domain.form.presentation.dto.CreatePostRoomFormDTO;
import kr.co.F1FS.app.domain.form.presentation.dto.ModifyPostRoomFormDTO;
import kr.co.F1FS.app.domain.form.presentation.dto.admin.UpdatePostRoomFormDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.AuthorCertification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostRoomFormDomainService {
    private final FormMapper formMapper;

    public PostRoomForm createEntity(CreatePostRoomFormDTO dto, User user){
        return formMapper.toPostRoomForm(dto, user);
    }

    public void confirm(UpdatePostRoomFormDTO dto, PostRoomForm postRoomForm){
        postRoomForm.confirm(dto);
    }

    public void modify(ModifyPostRoomFormDTO dto, PostRoomForm postRoomForm){
        postRoomForm.modify(dto);
    }

    public boolean certification(User user, PostRoomForm postRoomForm){
        return AuthorCertification.certification(user.getUsername(), postRoomForm.getUser().getUsername());
    }
}
