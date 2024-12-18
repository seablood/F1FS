package kr.co.F1FS.app.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "driver_record_relation")
public class DriverRecordRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "driver_id", foreignKey = @ForeignKey(name = "FK_DRR_driver_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Driver driverInfo;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "currentSeason_id", foreignKey = @ForeignKey(name = "FK_DRR_currentSeason_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CurrentSeason currentSeason;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "sinceDebut_id", foreignKey = @ForeignKey(name = "FK_DRR_sinceDebut_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SinceDebut sinceDebut;

    @Builder
    public DriverRecordRelation(Driver driver, CurrentSeason currentSeason, SinceDebut sinceDebut){
        this.driverInfo = driver;
        this.currentSeason = currentSeason;
        this.sinceDebut = sinceDebut;
    }
}
