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
import java.util.List;

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

    private List<String> tags;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;

    @Field(type = FieldType.Integer)
    private int likeNum;

    public void modify(Post post, List<String> tags){
        this.title = post.getTitle();
        this.content = post.getContent();
        this.tags = tags;
    }

    public void increaseLikeNum(){
        this.likeNum++;
    }

    public void decreaseLikeNum(){
        this.likeNum--;
    }

    @Builder
    public PostDocument(Post post, List<String> tags){
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author = post.getAuthor().getNickname();
        this.tags = tags;
        this.createdAt = TimeUtil.convertToKoreanTime(post.getCreatedAt());
        this.likeNum = post.getLikeNum();
    }
}
