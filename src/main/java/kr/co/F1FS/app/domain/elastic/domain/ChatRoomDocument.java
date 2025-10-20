package kr.co.F1FS.app.domain.elastic.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.F1FS.app.domain.chat.domain.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDateTime;

@Document(indexName = "chat_room")
@Setting(settingPath = "/elastic/chatRoom-setting.json")
@Mapping(mappingPath = "/elastic/chatRoom-mapping.json")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDocument {
    @Id
    private Long id;
    @Field(type = FieldType.Text)
    private String name;
    @Field(type = FieldType.Text)
    private String description;
    @Field(type = FieldType.Integer)
    private int memberCount;
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createTime;

    public void modify(ChatRoom chatRoom){
        this.name = chatRoom.getName();
        this.description = chatRoom.getDescription();
    }

    public void increaseMemberCount(){
        this.memberCount++;
    }

    public void decreaseMemberCount(){
        this.memberCount--;
    }

    @Builder
    public ChatRoomDocument(ChatRoom chatRoom){
        this.id = chatRoom.getId();
        this.name = chatRoom.getName();
        this.description = chatRoom.getDescription();
        this.memberCount = chatRoom.getMemberCount();
    }
}
