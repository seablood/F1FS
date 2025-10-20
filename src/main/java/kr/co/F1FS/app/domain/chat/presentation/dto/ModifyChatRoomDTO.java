package kr.co.F1FS.app.domain.chat.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModifyChatRoomDTO {
    @NotBlank(message = "name은 필수 항목입니다.")
    private String name;
    @NotBlank(message = "description은 필수 항목입니다.")
    private String description;
}
