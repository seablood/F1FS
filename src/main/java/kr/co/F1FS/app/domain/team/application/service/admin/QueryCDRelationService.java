package kr.co.F1FS.app.domain.team.application.service.admin;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.team.application.port.in.admin.QueryCDRelationUseCase;
import kr.co.F1FS.app.domain.team.application.port.out.CDRelationJpaPort;
import kr.co.F1FS.app.domain.team.domain.ConstructorDriverRelation;
import kr.co.F1FS.app.global.util.RacingClass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryCDRelationService implements QueryCDRelationUseCase {
    private final CDRelationJpaPort relationJpaPort;

    @Override
    public ConstructorDriverRelation findConstructorDriverRelationByDriverAndRacingClass(Driver driver, RacingClass racingClass) {
        return relationJpaPort.findConstructorDriverRelationByDriverAndRacingClass(driver, racingClass);
    }

    @Override
    public List<ConstructorDriverRelation> findConstructorDriverRelationByConstructor(Constructor constructor) {
        return relationJpaPort.findConstructorDriverRelationByConstructor(constructor);
    }
}
