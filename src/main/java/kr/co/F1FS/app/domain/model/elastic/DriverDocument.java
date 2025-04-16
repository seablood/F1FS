package kr.co.F1FS.app.domain.model.elastic;

import kr.co.F1FS.app.domain.model.rdb.Driver;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Document(indexName = "drivers")
@Setting(settingPath = "/elastic/driver-setting.json")
@Mapping(mappingPath = "/elastic/driver-mapping.json")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDocument {
    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String korName;

    @Field(type = FieldType.Text)
    private String engName;

    @Field(type = FieldType.Text)
    private String team;

    @Field(type = FieldType.Text)
    private String engTeam;

    @Builder
    public DriverDocument(Driver driver){
        this.id = driver.getId();
        this.korName = driver.getName();
        this.engName = driver.getEngName();
        this.team = driver.getTeam();
        this.engTeam = driver.getEngTeam();
    }
}
