package kr.co.F1FS.app.domain.model.elastic;

import kr.co.F1FS.app.domain.model.rdb.Constructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Document(indexName = "constructors")
@Setting(settingPath = "/elastic/constructor-setting.json")
@Mapping(mappingPath = "/elastic/constructor-mapping.json")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConstructorDocument {
    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String korName;

    @Field(type = FieldType.Text)
    private String engName;

    @Field(type = FieldType.Text)
    private String racingClass;

    @Builder
    public ConstructorDocument(Constructor constructor){
        this.id = constructor.getId();
        this.korName = constructor.getName();
        this.engName = constructor.getEngName();
        this.racingClass = constructor.getRacingClass().toString();
    }
}
