package kr.co.F1FS.app.domain.reply.presentation.dto.replyComment;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateReplyCommentDTO {
    @NotBlank(message = "댓글의 내용을 작성해주세요.")
    private String content;
}
