package kr.co.F1FS.app.domain.elastic.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.F1FS.app.domain.bookmark.domain.Bookmark;
import kr.co.F1FS.app.global.util.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDateTime;

@Document(indexName = "bookmarks")
@Setting(settingPath = "/elastic/bookmark-setting.json")
@Mapping(mappingPath = "/elastic/bookmark-mapping.json")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkDocument {
    @Id
    private Long id;
    @Field(type = FieldType.Long)
    private Long postId;
    @Field(type = FieldType.Text)
    private String title;
    @Field(type = FieldType.Text)
    private String author;
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime markingTime;

    public void modify(String title){
        this.title = title;
    }

    @Builder
    public BookmarkDocument(Bookmark bookmark){
        this.id = bookmark.getId();
        this.postId = bookmark.getPost().getId();
        this.title = bookmark.getPost().getTitle();
        this.author = bookmark.getPost().getAuthor().getNickname();
        this.createdAt = TimeUtil.convertToKoreanTime(bookmark.getPost().getCreatedAt());
        this.markingTime = TimeUtil.convertToKoreanTime(bookmark.getMarkingTime());
    }
}
