package kr.co.F1FS.app.presentation.admin.post.dto;

import kr.co.F1FS.app.domain.model.rdb.PostComplain;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponsePostComplainDTO {
    private Long id;
    private Long postId;
    private String postTitle;
    private String fromUserNickname;
    private String description;
    private String paraphrase;

    public static ResponsePostComplainDTO toDto(PostComplain complain){
        return new ResponsePostComplainDTO(complain.getId(), complain.getToPost().getId(), complain.getToPost().getTitle(),
                complain.getFromUser().getNickname(), complain.getDescription(), complain.getParaphrase());
    }
}
