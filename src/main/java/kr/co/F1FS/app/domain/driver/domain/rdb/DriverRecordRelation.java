package kr.co.F1FS.app.domain.driver.domain.rdb;

import jakarta.persistence.*;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.global.util.RacingClass;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "driver_record_relation", indexes = {
        @Index(name = "idx_DRR_driver_id", columnList = "driver_id"),
        @Index(name = "idx_DRR_currentSeason_id", columnList = "currentSeason_id")
})
public class DriverRecordRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", foreignKey = @ForeignKey(name = "FK_DRR_driver_id"), nullable = false)
    private Driver driverInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currentSeason_id", foreignKey = @ForeignKey(name = "FK_DRR_currentSeason_id"), nullable = false)
    private CurrentSeason currentSeason;

    @Enumerated(value = EnumType.STRING)
    private RacingClass racingClass;
    private boolean entryClassSeason;

    public boolean isEntryClassSeason(){
        return this.entryClassSeason;
    }

    public void updateEntryClassSeason(boolean entryClassSeason){
        this.entryClassSeason = entryClassSeason;
    }

    @Builder
    public DriverRecordRelation(Driver driver, CurrentSeason currentSeason, RacingClass racingClass){
        this.driverInfo = driver;
        this.currentSeason = currentSeason;
        this.racingClass = racingClass;
        this.entryClassSeason = false;
    }
}
