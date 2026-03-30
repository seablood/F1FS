package kr.co.F1FS.app.domain.elastic.domain;

import kr.co.F1FS.app.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Document(indexName = "users")
@Setting(settingPath = "/elastic/user-setting.json")
@Mapping(mappingPath = "/elastic/user-mapping.json")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDocument {
    @Id
    private Long id;
    @Field(type = FieldType.Text)
    private String username;
    @Field(type = FieldType.Text)
    private String nickname;
    @Field(type = FieldType.Integer)
    private int followerNum;

    public void modify(User user){
        this.nickname = user.getNickname();
    }

    public void increaseFollowerNum(){
        this.followerNum++;
    }

    public void decreaseFollowerNum(){
        this.followerNum--;
    }

    @Builder
    public UserDocument(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.followerNum = 0;
    }
}
