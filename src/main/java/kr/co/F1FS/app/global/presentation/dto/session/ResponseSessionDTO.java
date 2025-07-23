package kr.co.F1FS.app.global.presentation.dto.session;

import kr.co.F1FS.app.global.presentation.dto.sessionresult.ResponseSessionResultDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseSessionDTO {
    private Long id;
    private String sessionTime;
    private List<ResponseSessionResultDTO> resultList;
}
