package kr.co.F1FS.app.domain.admin.post.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminResponsePostComplainDTO {
    private Long id;
    private Long postId;
    private String postTitle;
    private String fromUserNickname;
    private String description;
    private String paraphrase;
}
