package kr.co.F1FS.app.domain.constructor.application.port.out;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.team.domain.ConstructorDriverRelation;

import java.util.List;

public interface ConstructorTeamPort {
    List<ConstructorDriverRelation> getDrivers(Constructor constructor);
}
