package kr.co.F1FS.app.domain.postRoomSuspension.application.mapper;

import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import kr.co.F1FS.app.domain.postRoomSuspension.domain.PostRoomSuspension;
import kr.co.F1FS.app.domain.postRoomSuspension.presentation.dto.CreatePostRoomSuspensionDTO;
import kr.co.F1FS.app.domain.user.domain.User;
import kr.co.F1FS.app.global.presentation.dto.postRoomSuspension.ResponsePostRoomSuspensionDTO;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class PostRoomSuspensionMapper {
    public PostRoomSuspension toPostRoomSuspension(CreatePostRoomSuspensionDTO dto, User suspendUser, PostRoom postRoom){
        LocalDateTime now = LocalDateTime.now();
        Timestamp suspendUntil = Timestamp.valueOf(now.plusDays(dto.getDurationDays()));

        return PostRoomSuspension.builder()
                .suspendUser(suspendUser)
                .postRoom(postRoom)
                .durationDays(dto.getDurationDays())
                .suspendUntil(suspendUntil)
                .startSuspension(now)
                .description(dto.getDescription())
                .paraphrase(dto.getParaphrase())
                .build();
    }

    public ResponsePostRoomSuspensionDTO toResponsePostRoomSuspensionDTO(PostRoomSuspension postRoomSuspension){
        return ResponsePostRoomSuspensionDTO.builder()
                .id(postRoomSuspension.getId())
                .suspendUser(postRoomSuspension.getSuspendUser().getNickname())
                .postRoom(postRoomSuspension.getPostRoom().getRoomTitle())
                .durationDays(postRoomSuspension.getDurationDays())
                .description(postRoomSuspension.getDescription())
                .paraphrase(postRoomSuspension.getParaphrase())
                .build();
    }
}
