package kr.co.F1FS.app.domain.model.rdb;

import jakarta.persistence.*;
import kr.co.F1FS.app.util.RacingClass;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "driver_id", foreignKey = @ForeignKey(name = "FK_DDR_driver_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Driver driverSinceInfo;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "sinceDebut_id", foreignKey = @ForeignKey(name = "FK_DDR_sinceDebut_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
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
