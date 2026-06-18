package kr.co.F1FS.app.domain.form.application.mapper;

import kr.co.F1FS.app.domain.form.domain.PostRoomDeleteForm;
import kr.co.F1FS.app.domain.form.domain.PostRoomForm;
import kr.co.F1FS.app.domain.form.presentation.dto.CreatePostRoomDeleteFormDTO;
import kr.co.F1FS.app.domain.form.presentation.dto.CreatePostRoomFormDTO;
import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.form.ResponsePostRoomDeleteFormDTO;
import kr.co.F1FS.app.global.presentation.dto.form.ResponsePostRoomFormDTO;
import org.springframework.stereotype.Component;

@Component
public class FormMapper {
    public PostRoomForm toPostRoomForm(CreatePostRoomFormDTO dto, User user){
        return PostRoomForm.builder()
                .user(user)
                .roomTitle(dto.getRoomTitle())
                .description(dto.getDescription())
                .isPublic(dto.isPublic())
                .password(dto.getPassword())
                .build();
    }

    public PostRoomDeleteForm toPostRoomDeleteForm(CreatePostRoomDeleteFormDTO dto, User user, PostRoom postRoom){
        return PostRoomDeleteForm.builder()
                .user(user)
                .postRoom(postRoom)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .isPostDelete(dto.isPostDelete())
                .build();
    }

    public ResponsePostRoomFormDTO toResponsePostRoomFormDTO(PostRoomForm postRoomForm){
        if(postRoomForm.getPassword() == null){
            return ResponsePostRoomFormDTO.builder()
                    .id(postRoomForm.getId())
                    .nickname(postRoomForm.getUser().getNickname())
                    .roomTitle(postRoomForm.getRoomTitle())
                    .description(postRoomForm.getDescription())
                    .createdAt(postRoomForm.getCreatedAt())
                    .isPublic(postRoomForm.isPublic())
                    .password("없음")
                    .build();
        }

        return ResponsePostRoomFormDTO.builder()
                .id(postRoomForm.getId())
                .nickname(postRoomForm.getUser().getNickname())
                .roomTitle(postRoomForm.getRoomTitle())
                .description(postRoomForm.getDescription())
                .createdAt(postRoomForm.getCreatedAt())
                .isPublic(postRoomForm.isPublic())
                .password("*****")
                .build();
    }

    public ResponsePostRoomDeleteFormDTO toResponsePostRoomDeleteFormDTO(PostRoomDeleteForm postRoomDeleteForm){
        return ResponsePostRoomDeleteFormDTO.builder()
                .id(postRoomDeleteForm.getId())
                .userNickname(postRoomDeleteForm.getUser().getNickname())
                .postRoomTitle(postRoomDeleteForm.getPostRoom().getRoomTitle())
                .postRomId(postRoomDeleteForm.getPostRoom().getId())
                .title(postRoomDeleteForm.getTitle())
                .description(postRoomDeleteForm.getDescription())
                .isConfirmed(postRoomDeleteForm.isConfirmed())
                .createdAt(postRoomDeleteForm.getCreatedAt())
                .build();
    }
}
