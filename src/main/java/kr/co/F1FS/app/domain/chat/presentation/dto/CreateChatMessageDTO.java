package kr.co.F1FS.app.domain.chat.presentation.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.co.F1FS.app.global.util.ChatStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateChatMessageDTO {
    private String content;
    private String imageUrl;
    @Enumerated(value = EnumType.STRING)
    private ChatStatus chatStatus;
}
