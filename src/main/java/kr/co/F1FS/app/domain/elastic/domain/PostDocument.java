package kr.co.F1FS.app.domain.elastic.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.F1FS.app.domain.post.domain.Post;
import kr.co.F1FS.app.global.util.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDateTime;

@Document(indexName = "posts")
@Setting(settingPath = "/elastic/post-setting.json")
@Mapping(mappingPath = "/elastic/post-mapping.json")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDocument {
    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Text)
    private String author;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @Field(type = FieldType.Integer)
    private int likeNum;

    public void modify(Post post){
        this.title = post.getTitle();
        this.content = post.getContent();
    }

    public void increaseLikeNum(){
        this.likeNum++;
    }

    public void decreaseLikeNum(){
        this.likeNum--;
    }

    @Builder
    public PostDocument(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = post.getAuthor().getNickname();
        this.createdAt = TimeUtil.convertToKoreanTime(post.getCreatedAt());
        this.likeNum = post.getLikeNum();
    }
}
