package kr.co.F1FS.app.domain.elastic.domain;

import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

@Document(indexName = "grand_prix_suggest")
@Setting(settingPath = "/elastic/grandprixSuggest-setting.json")
@Mapping(mappingPath = "/elastic/grandprixSuggest-mapping.json")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrandPrixSuggestDocument {
    @Id
    private Long id;
    @Field(type = FieldType.Text)
    private String korName;
    @Field(type = FieldType.Text)
    private String engName;

    @Builder
    public GrandPrixSuggestDocument(GrandPrix grandPrix){
        this.id = grandPrix.getId();
        this.korName = grandPrix.getName();
        this.engName = grandPrix.getEngName();
    }
}
