package kr.co.F1FS.app.global.presentation.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseChatRoomDocumentDTO {
    private Long id;
    private String name;
    private String description;
    private int memberCount;
}
