package kr.co.F1FS.app.domain.elastic.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.F1FS.app.domain.postRoom.domain.PostRoom;
import kr.co.F1FS.app.global.util.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDateTime;

@Document(indexName = "post_room")
@Setting(settingPath = "/elastic/postRoom-setting.json")
@Mapping(mappingPath = "/elastic/postRoom-mapping.json")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRoomDocument {
    @Id
    private Long id;
    @Field(type = FieldType.Text)
    private String roomTitle;
    @Field(type = FieldType.Text)
    private String description;
    @Field(type = FieldType.Text)
    private String masterUser;
    @Field(type = FieldType.Boolean)
    private boolean isPublic;
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createTime;

    public void modifyInfo(PostRoom postRoom){
        this.roomTitle = postRoom.getRoomTitle();
        this.description = postRoom.getDescription();
    }

    public void modifyIsPublic(PostRoom postRoom){
        this.isPublic = postRoom.isPublic();
    }

    @Builder
    public PostRoomDocument(PostRoom postRoom){
        this.id = postRoom.getId();
        this.roomTitle = postRoom.getRoomTitle();
        this.description = postRoom.getDescription();
        this.masterUser = postRoom.getMasterUser().getNickname();
        this.isPublic = postRoom.isPublic();
        this.createTime = TimeUtil.convertToKoreanTime(postRoom.getCreatedAt());
    }
}
