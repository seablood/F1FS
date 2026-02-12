package kr.co.F1FS.app.global.presentation.dto.complain.reply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleResponseReplyComplainDTO {
    private Long id;
    private String replyContent;
    private String description;
}
