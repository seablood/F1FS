package kr.co.F1FS.app.global.presentation.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseChatRoomDTO {
    private Long id;
    private String name;
    private String description;
    private int memberCount;
}
