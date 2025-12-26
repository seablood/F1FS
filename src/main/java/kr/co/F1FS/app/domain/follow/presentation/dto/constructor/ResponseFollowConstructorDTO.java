package kr.co.F1FS.app.domain.follow.presentation.dto.constructor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseFollowConstructorDTO {
    private String name;
    private String engName;
    private List<String> driverList;
}
