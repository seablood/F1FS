package kr.co.F1FS.app.global.util;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    public static LocalDateTime convertToKoreanTime(Timestamp timestamp) {
        // 1. Timestamp → Instant
        Instant instant = timestamp.toInstant();

        // 2. Instant → ZonedDateTime (Asia/Seoul)
        ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("Asia/Seoul"));

        // 3. ZonedDateTime → LocalDateTime
        return zonedDateTime.toLocalDateTime();
    }

    public static String formatPostTime(LocalDateTime timestamp) {
        LocalDate today = LocalDate.now();
        LocalDate postDate = timestamp.toLocalDate();

        if (postDate.isEqual(today)) {
            return timestamp.format(DateTimeFormatter.ofPattern("HH:mm"));
        } else {
            return timestamp.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        }
    }
}
