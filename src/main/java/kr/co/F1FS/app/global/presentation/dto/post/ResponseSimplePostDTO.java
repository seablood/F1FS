package kr.co.F1FS.app.global.presentation.dto.post;

import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.global.util.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseSimplePostDTO {
    private Long id;
    private String title;
    private String author;
    private String createdAt;
    private int likeNum;

    public static ResponseSimplePostDTO toDto(Post post){
        LocalDateTime postTime = TimeUtil.convertToKoreanTime(post.getCreatedAt());

        return new ResponseSimplePostDTO(post.getId(), post.getTitle(), post.getAuthor().getNickname(),
                TimeUtil.formatPostTime(postTime), post.getLikeNum());
    }
}
