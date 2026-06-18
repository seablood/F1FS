package kr.co.F1FS.app.domain.postRoom.application.mapper;

import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import kr.co.F1FS.app.domain.postRoom.presentation.dto.CreatePostRoomDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.postRoom.ResponsePostRoomDTO;
import org.springframework.stereotype.Component;

@Component
public class PostRoomMapper {
    public PostRoom toPostRoom(CreatePostRoomDTO dto, User user){
        return PostRoom.builder()
                .user(user)
                .roomTitle(dto.getRoomTitle())
                .description(dto.getDescription())
                .isPublic(dto.isPublic())
                .password(dto.getPassword())
                .build();
    }

    public ResponsePostRoomDTO toResponsePostRoomDTO(PostRoom postRoom){
        return ResponsePostRoomDTO.builder()
                .id(postRoom.getId())
                .masterNickname(postRoom.getMasterUser().getNickname())
                .roomTitle(postRoom.getRoomTitle())
                .description(postRoom.getDescription())
                .isPublic(postRoom.isPublic())
                .postCount(postRoom.getPostCount())
                .createdAt(postRoom.getCreatedAt())
                .updatedAt(postRoom.getUpdatedAt())
                .build();
    }
}
