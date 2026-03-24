package kr.co.F1FS.app.domain.team.application.port.in.admin;

import kr.co.F1FS.app.domain.team.domain.ConstructorDriverRelation;
import kr.co.F1FS.app.global.util.RacingClass;

import java.util.List;

public interface QueryCDRelationUseCase {
    ConstructorDriverRelation findByDriverAndRacingClass(Long driverId, RacingClass racingClass);
    List<ConstructorDriverRelation> findAllByConstructor(Long constructorId);
}
