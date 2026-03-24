package kr.co.F1FS.app.domain.driver.domain.rdb;

import jakarta.persistence.*;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import kr.co.F1FS.app.global.util.RacingClass;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "driver_debut_relation", indexes = {
        @Index(name = "idx_DDR_driver_id", columnList = "driver_id"),
        @Index(name = "idx_DDR_sinceDebut_id", columnList = "sinceDebut_id")
})
public class DriverDebutRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", foreignKey = @ForeignKey(name = "FK_DDR_driver_id"), nullable = false)
    private Driver driverSinceInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sinceDebut_id", foreignKey = @ForeignKey(name = "FK_DDR_sinceDebut_id"), nullable = false)
    private SinceDebut sinceDebut;

    @Enumerated(value = EnumType.STRING)
    private RacingClass racingClass;

    @Builder
    public DriverDebutRelation(Driver driver, SinceDebut sinceDebut, RacingClass racingClass){
        this.driverSinceInfo = driver;
        this.sinceDebut = sinceDebut;
        this.racingClass = racingClass;
    }
}
