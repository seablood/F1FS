package kr.co.F1FS.app.model;

import jakarta.persistence.*;
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

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "constructor_id", foreignKey = @ForeignKey(name = "FK_CDR_constructor_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Constructor constructor;

    // 릴레이션 테이블 삭제 가능을 위한 단방향 일대일 관계 생성
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "driver_id", foreignKey = @ForeignKey(name = "FK_CDR_driver_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Driver driver;

    @Builder
    public ConstructorDriverRelation(Constructor constructor, Driver driver){
        this.constructor = constructor;
        this.driver = driver;
    }

}
