package kr.co.F1FS.app.domain.elastic.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagListRequestDTO {
    private List<String> tags;
}
