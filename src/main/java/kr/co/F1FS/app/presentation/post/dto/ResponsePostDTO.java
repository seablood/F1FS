package kr.co.F1FS.app.presentation.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.F1FS.app.domain.model.rdb.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePostDTO {
    private Long id;
    private String title;
    private String content;
    private String author;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp updatedAt;
    private int likeNum;

    public static ResponsePostDTO toDto(Post post){
        return new ResponsePostDTO(post.getId(), post.getTitle(), post.getContent(), post.getAuthor().getNickname(),
                post.getCreatedAt(), post.getUpdatedAt(), post.getLikeNum());
    }
}
