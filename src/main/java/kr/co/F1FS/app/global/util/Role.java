package kr.co.F1FS.app.global.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER"), TEMPORARY("ROLE_TEMPORARY"), DORMANT("ROLE_DORMANT"),
    DISCIPLINE("ROLE_DISCIPLINE");

    private final String key;
}
