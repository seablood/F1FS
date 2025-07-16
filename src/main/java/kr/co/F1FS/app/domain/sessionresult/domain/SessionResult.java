package kr.co.F1FS.app.domain.sessionresult.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.session.domain.Session;
import kr.co.F1FS.app.global.util.RaceStatus;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "session_results")
public class SessionResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "session_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Session session;
    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;
    @ManyToOne
    @JoinColumn(name = "constructor_id")
    private Constructor constructor;
    @Positive(message = "position값을 확인해주세요.")
    private Integer position;
    private String timeOrGap;
    @Max(25)
    @Min(0)
    private Integer points;
    private boolean isFastestLap;
    @Enumerated(value = EnumType.STRING)
    private RaceStatus raceStatus;

    @Builder
    public SessionResult(Session session, Driver driver, Constructor constructor, Integer position, String timeOrGap,
                         Integer points, boolean isFastestLap, RaceStatus raceStatus){
        this.session = session;
        this.driver = driver;
        this.constructor = constructor;
        this.position = position;
        this.timeOrGap = timeOrGap;
        this.points = points;
        this.isFastestLap = isFastestLap;
        this.raceStatus = raceStatus;
    }
}
