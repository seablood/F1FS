package kr.co.F1FS.app.global.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
    OFFICIAL("OFFICIAL"), PERSONAL("PERSONAL");

    private final String type;
}
