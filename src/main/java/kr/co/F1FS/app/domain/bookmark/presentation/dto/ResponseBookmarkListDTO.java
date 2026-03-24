package kr.co.F1FS.app.domain.bookmark.presentation.dto;

import com.querydsl.core.annotations.QueryProjection;
import kr.co.F1FS.app.global.util.TimeUtil;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ResponseBookmarkListDTO {
    private Long postId;
    private String title;
    private String author;
    private int likeNum;
    private String createdAt;

    @QueryProjection
    public ResponseBookmarkListDTO(Long postId, String title, String author, int likeNum, Timestamp createdAt){
        this.postId = postId;
        this.title = title;
        this.author = author;
        this.likeNum = likeNum;
        this.createdAt = TimeUtil.formatPostTime(TimeUtil.convertToKoreanTime(createdAt));
    }
}
