package kr.co.F1FS.app.global.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Color {
    RED("#ff0000"),
    YELLOW("#ffff9f");

    private final String code;
}
