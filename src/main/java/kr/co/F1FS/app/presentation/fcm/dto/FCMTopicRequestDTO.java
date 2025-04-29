package kr.co.F1FS.app.presentation.fcm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FCMTopicRequestDTO {
    private String token;
    private String topic;
}
