package kr.co.F1FS.app.global.presentation.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseChatMessageDTO {
    private String sender;
    private String content;
    private String imageUrl;
    private String sendTime;
}
