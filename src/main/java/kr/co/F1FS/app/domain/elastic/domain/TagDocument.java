package kr.co.F1FS.app.domain.elastic.domain;

import kr.co.F1FS.app.domain.tag.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Document(indexName = "tags")
@Setting(settingPath = "/elastic/tag-setting.json")
@Mapping(mappingPath = "/elastic/tag-mapping.json")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDocument {
    @Id
    private Long id;
    @Field(type = FieldType.Text)
    private String name;

    @Builder
    public TagDocument(Tag tag){
        this.id = tag.getId();
        this.name = tag.getName();
    }
}
