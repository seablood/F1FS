package kr.co.F1FS.app.global.config.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailQueueMemory {
    private String to;
    private String subject;
    private String content;
    private EmailType emailType;
}
