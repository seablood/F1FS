package kr.co.F1FS.app.domain.team.domain;

import jakarta.persistence.*;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.global.util.RacingClass;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "constructor_driver_relation", indexes = {
        @Index(name = "idx_CDR_driver_id", columnList = "driver_id"),
        @Index(name = "idx_CDR_constructor_id", columnList = "constructor_id")
})
public class ConstructorDriverRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "constructor_id", foreignKey = @ForeignKey(name = "FK_CDR_constructor_id"), nullable = false)
    private Constructor constructor;

    // 릴레이션 테이블 삭제 가능을 위한 단방향 일대일 관계 생성
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", foreignKey = @ForeignKey(name = "FK_CDR_driver_id"), nullable = false)
    private Driver driver;

    @Enumerated(value = EnumType.STRING)
    private RacingClass racingClass;

    public void updateConstructor(Constructor constructor){
        this.constructor = constructor;
    }

    @Builder
    public ConstructorDriverRelation(Constructor constructor, Driver driver, RacingClass racingClass){
        this.constructor = constructor;
        this.driver = driver;
        this.racingClass = racingClass;
    }

}
