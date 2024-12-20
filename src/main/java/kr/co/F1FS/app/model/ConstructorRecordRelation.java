package kr.co.F1FS.app.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "constructor_record_relation", indexes = {
        @Index(name = "idx_CRR_constructor_id", columnList = "constructor_id"),
        @Index(name = "idx_CRR_currentSeason_id", columnList = "currentSeason_id"),
        @Index(name = "idx_CRR_sinceDebut_id", columnList = "sinceDebut_id")
})
public class ConstructorRecordRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "constructor_id", foreignKey = @ForeignKey(name = "FK_CRR_constructor_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Constructor constructorInfo;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "currentSeason_id", foreignKey = @ForeignKey(name = "FK_CRR_currentSeason_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CurrentSeason currentSeason;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "sinceDebut_id", foreignKey = @ForeignKey(name = "FK_CRR_sinceDebut_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SinceDebut sinceDebut;

    @Builder
    public ConstructorRecordRelation(Constructor constructor, CurrentSeason currentSeason, SinceDebut sinceDebut){
        this.constructorInfo = constructor;
        this.currentSeason = currentSeason;
        this.sinceDebut = sinceDebut;
    }
}
