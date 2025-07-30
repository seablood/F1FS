package kr.co.F1FS.app.domain.grandprix.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import kr.co.F1FS.app.domain.grandprix.presentation.dto.ModifyGrandPrixCommand;
import kr.co.F1FS.app.global.util.RacingClass;
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
    @Column(name = "first_session_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp firstSessionTime;
    @Column(name = "last_session_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Timestamp lastSessionTime;
    private Long firstPractice;
    private Long secondPractice;
    private Long thirdPractice;
    private Long sprintQualifying;
    private Long qualifying;
    private Long sprint;
    private Long race;
    @Lob
    private String description;
    @Positive(message = "정확한 ID를 입력하세요.")
    private Long circuitId;
    private Integer season;
    private Integer round;
    private String firstPracticeTime;
    private String secondPracticeTime;
    private String thirdPracticeTime;
    private String sprintQualifyingTime;
    private String qualifyingTime;
    private String sprintTime;
    private String raceTime;
    @Enumerated(value = EnumType.STRING)
    private RacingClass racingClass;

    public void setFirstPractice(Long firstPractice) {
        this.firstPractice = firstPractice;
    }

    public void setSecondPractice(Long secondPractice) {
        this.secondPractice = secondPractice;
    }

    public void setThirdPractice(Long thirdPractice) {
        this.thirdPractice = thirdPractice;
    }

    public void setSprintQualifying(Long sprintQualifying) {
        this.sprintQualifying = sprintQualifying;
    }

    public void setQualifying(Long qualifying) {
        this.qualifying = qualifying;
    }

    public void setSprint(Long sprint) {
        this.sprint = sprint;
    }

    public void setRace(Long race) {
        this.race = race;
    }

    public void modify(ModifyGrandPrixCommand command){
        this.name = command.getName();
        this.engName = command.getEngName();
        this.description = command.getDescription();
        this.circuitId = command.getCircuitId();
    }

    public boolean isThisWeek(){
        Timestamp grandTime = this.firstSessionTime;

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
    public GrandPrix(String name, String engName, Timestamp firstSessionTime, Timestamp lastSessionTime,
                     String firstPracticeTime, String secondPracticeTime, String thirdPracticeTime,
                     String sprintQualifyingTime, String qualifyingTime, String sprintTime, String raceTime,
                     String description, Long circuitId, Integer season, Integer round, RacingClass racingClass){
        this.name = name;
        this.engName = engName;
        this.firstSessionTime = firstSessionTime;
        this.lastSessionTime = lastSessionTime;
        this.firstPracticeTime = firstPracticeTime;
        this.secondPracticeTime = secondPracticeTime;
        this.thirdPracticeTime = thirdPracticeTime;
        this.sprintQualifyingTime = sprintQualifyingTime;
        this.qualifyingTime = qualifyingTime;
        this.sprintTime = sprintTime;
        this.raceTime = raceTime;
        this.description = description;
        this.circuitId = circuitId;
        this.season = season;
        this.round = round;
        this.racingClass = racingClass;
    }
}
