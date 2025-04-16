package kr.co.F1FS.app.global.config.email;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmailType {
    CREATE_ACCOUNT("create_account", "계정 생성을 위한 인증", "email.html"),
    UPDATE_PASSWORD("update_password", "비밀번호 변경을 위한 인증", "email.html");

    private final String key;
    private final String description;
    private final String templateName;
}
