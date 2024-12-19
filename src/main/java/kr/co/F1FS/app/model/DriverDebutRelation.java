package kr.co.F1FS.app.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "driver_debut_relation")
public class DriverDebutRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "driver_id", foreignKey = @ForeignKey(name = "FK_DDR_driver_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Driver driverSinceInfo;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "sinceDebut_id", foreignKey = @ForeignKey(name = "FK_DDR_sinceDebut_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SinceDebut sinceDebut;

    @Builder
    public DriverDebutRelation(Driver driver, SinceDebut sinceDebut){
        this.driverSinceInfo = driver;
        this.sinceDebut = sinceDebut;
    }
}
