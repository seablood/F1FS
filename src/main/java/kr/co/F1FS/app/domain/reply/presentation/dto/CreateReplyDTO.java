package kr.co.F1FS.app.domain.reply.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.domain.reply.domain.Reply;
import kr.co.F1FS.app.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateReplyDTO {
    @NotBlank(message = "댓글의 내용을 작성해주세요.")
    private String content;

    public static Reply toEntity(CreateReplyDTO dto, Post post, User user){
        return Reply.builder()
                .content(dto.getContent())
                .post(post)
                .user(user)
                .build();
    }
}
