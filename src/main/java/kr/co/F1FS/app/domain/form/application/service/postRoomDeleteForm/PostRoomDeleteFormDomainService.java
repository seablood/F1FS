package kr.co.F1FS.app.domain.form.application.service.postRoomDeleteForm;

import kr.co.F1FS.app.domain.form.application.mapper.FormMapper;
import kr.co.F1FS.app.domain.form.domain.PostRoomDeleteForm;
import kr.co.F1FS.app.domain.form.presentation.dto.CreatePostRoomDeleteFormDTO;
import kr.co.F1FS.app.domain.form.presentation.dto.ModifyPostRoomDeleteFormDTO;
import kr.co.F1FS.app.domain.form.presentation.dto.admin.UpdatePostRoomDeleteFormDTO;
import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.util.AuthorCertification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostRoomDeleteFormDomainService {
    private final FormMapper formMapper;

    public PostRoomDeleteForm createEntity(CreatePostRoomDeleteFormDTO dto, User user, PostRoom postRoom){
        return formMapper.toPostRoomDeleteForm(dto, user, postRoom);
    }

    public void confirm(UpdatePostRoomDeleteFormDTO dto, PostRoomDeleteForm deleteForm){
        deleteForm.confirm(dto);
    }

    public void modify(ModifyPostRoomDeleteFormDTO dto, PostRoomDeleteForm deleteForm){
        deleteForm.modify(dto);
    }

    public boolean certification(User user, PostRoomDeleteForm deleteForm){
        return AuthorCertification.certification(user.getUsername(), deleteForm.getUser().getUsername());
    }
}
