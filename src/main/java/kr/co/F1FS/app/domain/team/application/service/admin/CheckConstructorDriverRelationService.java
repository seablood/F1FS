package kr.co.F1FS.app.domain.team.application.service.admin;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.team.application.port.in.admin.CheckConstructorDriverRelationUseCase;
import kr.co.F1FS.app.domain.team.application.port.out.CDRelationJpaPort;
import kr.co.F1FS.app.global.util.RacingClass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckConstructorDriverRelationService implements CheckConstructorDriverRelationUseCase {
    private final CDRelationJpaPort cdRelationJpaPort;

    @Override
    public boolean existsConstructorDriverRelationByDriverAndRacingClass(Driver driver, RacingClass racingClass) {
        return cdRelationJpaPort.existsConstructorDriverRelationByDriverAndRacingClass(driver, racingClass);
    }

    @Override
    public boolean existsConstructorDriverRelationByDriverAndConstructor(Driver driver, Constructor constructor) {
        return cdRelationJpaPort.existsConstructorDriverRelationByDriverAndConstructor(driver, constructor);
    }
}
