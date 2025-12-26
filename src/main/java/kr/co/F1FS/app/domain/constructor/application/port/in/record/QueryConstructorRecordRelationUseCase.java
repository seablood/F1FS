package kr.co.F1FS.app.domain.constructor.application.port.in.record;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.domain.ConstructorRecordRelation;

public interface QueryConstructorRecordRelationUseCase {
    ConstructorRecordRelation findByConstructor(Constructor constructor);
}
