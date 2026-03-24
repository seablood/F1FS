package kr.co.F1FS.app.domain.constructor.domain;

import jakarta.persistence.*;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import kr.co.F1FS.app.global.util.RacingClass;
import lombok.*;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "constructor_id", foreignKey = @ForeignKey(name = "FK_CRR_constructor_id"), nullable = false)
    private Constructor constructorInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currentSeason_id", foreignKey = @ForeignKey(name = "FK_CRR_currentSeason_id"), nullable = false)
    private CurrentSeason currentSeason;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sinceDebut_id", foreignKey = @ForeignKey(name = "FK_CRR_sinceDebut_id"), nullable = false)
    private SinceDebut sinceDebut;
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
    public ConstructorRecordRelation(Constructor constructor, CurrentSeason currentSeason, SinceDebut sinceDebut,
                                     RacingClass racingClass){
        this.constructorInfo = constructor;
        this.currentSeason = currentSeason;
        this.sinceDebut = sinceDebut;
        this.racingClass = racingClass;
        this.entryClassSeason = false;
    }
}
