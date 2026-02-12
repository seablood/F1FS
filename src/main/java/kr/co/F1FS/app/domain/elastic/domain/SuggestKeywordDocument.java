package kr.co.F1FS.app.domain.elastic.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDateTime;

@Document(indexName = "suggest_keyword")
@Setting(settingPath = "/elastic/suggestKeyword-setting.json")
@Mapping(mappingPath = "/elastic/suggestKeyword-mapping.json")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SuggestKeywordDocument {
    @Id
    private String suggest;
    @Field(type = FieldType.Long)
    private Long searchCount;
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime lastSearchedAt;
}
