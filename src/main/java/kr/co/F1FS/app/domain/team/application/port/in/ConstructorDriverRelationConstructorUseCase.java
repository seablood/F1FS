package kr.co.F1FS.app.domain.team.application.port.in;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.team.domain.ConstructorDriverRelation;

import java.util.List;

public interface ConstructorDriverRelationConstructorUseCase {
    List<ConstructorDriverRelation> findConstructorDriverRelationByConstructor(Constructor constructor);
}
