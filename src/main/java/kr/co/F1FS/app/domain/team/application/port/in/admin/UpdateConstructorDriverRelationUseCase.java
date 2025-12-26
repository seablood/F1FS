package kr.co.F1FS.app.domain.team.application.port.in.admin;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.team.domain.ConstructorDriverRelation;

public interface UpdateConstructorDriverRelationUseCase {
    void updateConstructor(ConstructorDriverRelation relation, Constructor constructor);
}
