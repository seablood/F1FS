package kr.co.F1FS.app.domain.post.presentation.dto;

import com.querydsl.core.annotations.QueryProjection;
import kr.co.F1FS.app.global.util.TimeUtil;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ResponsePostListDTO {
    private Long id;
    private String title;
    private String author;
    private String roomTitle;
    private String createdAt;
    private int likeNum;

    @QueryProjection
    public ResponsePostListDTO(Long id, String title, String author, String roomTitle, Timestamp createdAt, int likeNum){
        this.id = id;
        this.title = title;
        this.author = author;
        this.roomTitle = roomTitle;
        this.createdAt = TimeUtil.formatPostTime(TimeUtil.convertToKoreanTime(createdAt));
        this.likeNum = likeNum;
    }
}
