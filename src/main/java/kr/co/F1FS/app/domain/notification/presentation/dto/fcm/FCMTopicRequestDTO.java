package kr.co.F1FS.app.domain.notification.presentation.dto.fcm;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.co.F1FS.app.global.util.Topic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FCMTopicRequestDTO {
    @Enumerated(value = EnumType.STRING)
    private Topic topic;
}
