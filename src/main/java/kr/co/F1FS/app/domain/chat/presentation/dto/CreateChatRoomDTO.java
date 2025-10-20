package kr.co.F1FS.app.domain.chat.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateChatRoomDTO {
    @NotBlank(message = "name은 필수 항목입니다.")
    private String name;
    @NotBlank(message = "description은 필수 항목입니다.")
    private String description;
}
