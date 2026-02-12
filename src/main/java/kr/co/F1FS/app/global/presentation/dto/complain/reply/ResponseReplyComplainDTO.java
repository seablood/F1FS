package kr.co.F1FS.app.global.presentation.dto.complain.reply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseReplyComplainDTO {
    private Long id;
    private Long replyId;
    private String replyContent;
    private String fromUserNickname;
    private String description;
    private String paraphrase;
}
