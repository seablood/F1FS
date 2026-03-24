package kr.co.F1FS.app.domain.team.application.port.out;

import kr.co.F1FS.app.domain.team.domain.ConstructorDriverRelation;
import kr.co.F1FS.app.global.util.RacingClass;

import java.util.List;

public interface CDRelationJpaPort {
    ConstructorDriverRelation save(ConstructorDriverRelation relation);
    ConstructorDriverRelation saveAndFlush(ConstructorDriverRelation relation);
    boolean existsByDriverAndRacingClass(Long driverId, RacingClass racingClass);
    boolean existsByDriverAndConstructor(Long driverId, Long constructorId);
    ConstructorDriverRelation findByDriverAndRacingClass(Long driverId, RacingClass racingClass);
    List<ConstructorDriverRelation> findAllByDriver(Long driverId);
    List<ConstructorDriverRelation> findAllByConstructor(Long constructorId);
    void delete(ConstructorDriverRelation relation);
}
