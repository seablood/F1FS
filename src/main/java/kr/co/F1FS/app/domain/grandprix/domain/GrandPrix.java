package kr.co.F1FS.app.domain.grandprix.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import kr.co.F1FS.app.domain.grandprix.presentation.dto.ModifyGrandPrixCommand;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.Locale;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "grand_prix", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"season", "round"})
})
public class GrandPrix {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String engName;
    @Column(name = "first_practice_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp firstPracticeTime;
    @Column(name = "second_practice_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp secondPracticeTime;
    @Column(name = "third_practice_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp thirdPracticeTime;
    @Column(name = "sprint_qualifying_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp sprintQualifyingTime;
    @Column(name = "qualifying_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp qualifyingTime;
    @Column(name = "sprint_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp sprintTime;
    @Column(name = "race_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp raceTime;
    @Lob
    private String description;
    @Positive(message = "정확한 ID를 입력하세요.")
    private Long circuitId;
    private Integer season;
    private Integer round;

    public void modify(ModifyGrandPrixCommand command){
        this.name = command.getName();
        this.engName = command.getEngName();
        this.firstPracticeTime = command.getFirstPracticeTime();
        this.secondPracticeTime = command.getSecondPracticeTime();
        this.thirdPracticeTime = command.getThirdPracticeTime();
        this.sprintQualifyingTime = command.getSprintQualifyingTime();
        this.qualifyingTime = command.getQualifyingTime();
        this.sprintTime = command.getSprintTime();
        this.raceTime  = command.getRaceTime();
        this.description = command.getDescription();
        this.circuitId = command.getCircuitId();
    }

    public boolean isThisWeek(){
        Timestamp grandTime = this.firstPracticeTime;

        LocalDate raceDate = grandTime.toInstant()
                .atZone(ZoneId.of("Asia/Seoul"))
                .toLocalDate();
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));

        WeekFields weekFields = WeekFields.of(Locale.KOREA);

        int raceWeek = raceDate.get(weekFields.weekOfWeekBasedYear());
        int currentWeek = today.get(weekFields.weekOfWeekBasedYear());

        return raceDate.getYear() == today.getYear() && raceWeek == currentWeek;
    }

    @Builder
    public GrandPrix(String name, String engName, Timestamp firstPracticeTime, Timestamp secondPracticeTime,
                     Timestamp thirdPracticeTime, Timestamp sprintQualifyingTime, Timestamp qualifyingTime,
                     Timestamp sprintTime, Timestamp raceTime, String description, Long circuitId,
                     Integer season, Integer round){
        this.name = name;
        this.engName = engName;
        this.firstPracticeTime = firstPracticeTime;
        this.secondPracticeTime = secondPracticeTime;
        this.thirdPracticeTime = thirdPracticeTime;
        this.sprintQualifyingTime = sprintQualifyingTime;
        this.qualifyingTime = qualifyingTime;
        this.sprintTime = sprintTime;
        this.raceTime  =raceTime;
        this.description = description;
        this.circuitId = circuitId;
        this.season = season;
        this.round = round;
    }
}
