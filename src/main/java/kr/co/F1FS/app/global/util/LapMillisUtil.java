package kr.co.F1FS.app.global.util;

public class LapMillisUtil {
    public static Long fastestToMillis(String format){
        String[] parts = format.split(":");
        if (parts.length != 2) throw new IllegalArgumentException("잘못된 형식: " + format);

        long minutes = Long.parseLong(parts[0]);

        String[] secondsParts = parts[1].split("\\.");
        long seconds = Long.parseLong(secondsParts[0]);
        long millis = (secondsParts.length > 1) ? Long.parseLong(String.format("%-3s", secondsParts[1]).replace(' ', '0')) : 0;

        return (minutes * 60 + seconds) * 1000 + millis;
    }

    public static String fastestLapFormat(Long millis){
        long totalSeconds = millis.longValue() / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        long milliseconds = millis.longValue() % 1000;

        return String.format("%d:%02d.%03d", minutes, seconds, milliseconds);
    }
}
