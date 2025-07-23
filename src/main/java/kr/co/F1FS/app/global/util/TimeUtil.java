package kr.co.F1FS.app.global.util;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    public static String getGrandPrixDay(Timestamp start, Timestamp end){
        ZonedDateTime startTime = start.toInstant().atZone(ZoneId.of("Asia/Seoul"));
        ZonedDateTime endTime = end.toInstant().atZone(ZoneId.of("Asia/Seoul"));

        String startMonth = getMonth(startTime.getMonthValue());
        String endMonth = getMonth(endTime.getMonthValue());
        int startDay = startTime.getDayOfMonth();
        int endDay = endTime.getDayOfMonth();

        if(startMonth.equals(endMonth)){
            return startDay+"-"+endDay+" "+startMonth;
        }

        return startDay+" "+startMonth+"-"+endDay+" "+endMonth;
    }

    public static String getSessionTime(Timestamp time){
        if(time == null) return null;
        ZonedDateTime sessionTime = time.toInstant().atZone(ZoneId.of("Asia/Seoul"));

        int month = sessionTime.getMonthValue();
        int day = sessionTime.getDayOfMonth();
        String week = getWeek(sessionTime.getDayOfWeek());
        int hour = sessionTime.getHour();
        int min = sessionTime.getMinute();

        return String.format("%d월 %d일(%s)<br>%02d:%02d", month, day, week, hour, min);
    }

    public static String getMonth(int month){
        switch (month){
            case 3 :
                return "Mar";
            case 4 :
                return "Apr";
            case 5 :
                return "May";
            case 6 :
                return "Jun";
            case 7 :
                return "Jul";
            case 8 :
                return "Aug";
            case 9 :
                return "Sep";
            case 10 :
                return "Oct";
            case 11 :
                return "Nov";
            case 12 :
                return "Dec";
            default:
                return "";
        }
    }

    public static String getWeek(DayOfWeek week){
        switch (week){
            case MONDAY -> {
                return "월";
            }
            case TUESDAY -> {
                return "화";
            }
            case WEDNESDAY -> {
                return "수";
            }
            case THURSDAY -> {
                return "목";
            }
            case FRIDAY -> {
                return "금";
            }
            case SATURDAY -> {
                return "토";
            }
            case SUNDAY -> {
                return "일";
            }
            default -> {
                return "";
            }
        }
    }
}
