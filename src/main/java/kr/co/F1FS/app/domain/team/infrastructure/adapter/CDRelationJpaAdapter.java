package kr.co.F1FS.app.domain.team.infrastructure.adapter;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.team.application.port.out.CDRelationJpaPort;
import kr.co.F1FS.app.domain.team.domain.ConstructorDriverRelation;
import kr.co.F1FS.app.domain.team.infrastructure.repository.ConstructorDriverRelationRepository;
import kr.co.F1FS.app.global.util.RacingClass;
import kr.co.F1FS.app.global.util.exception.driver.DriverException;
import kr.co.F1FS.app.global.util.exception.driver.DriverExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CDRelationJpaAdapter implements CDRelationJpaPort {
    private final ConstructorDriverRelationRepository relationRepository;

    @Override
    public ConstructorDriverRelation save(ConstructorDriverRelation relation) {
        return relationRepository.save(relation);
    }

    @Override
    public ConstructorDriverRelation saveAndFlush(ConstructorDriverRelation relation) {
        return relationRepository.saveAndFlush(relation);
    }

    @Override
    public boolean existsConstructorDriverRelationByDriverAndRacingClass(Driver driver, RacingClass racingClass) {
        return relationRepository.existsConstructorDriverRelationByDriverAndRacingClass(driver, racingClass);
    }

    @Override
    public boolean existsConstructorDriverRelationByDriverAndConstructor(Driver driver, Constructor constructor) {
        return relationRepository.existsConstructorDriverRelationByDriverAndConstructor(driver, constructor);
    }

    @Override
    public ConstructorDriverRelation findConstructorDriverRelationByDriverAndRacingClass(Driver driver, RacingClass racingClass) {
        return relationRepository.findConstructorDriverRelationByDriverAndRacingClass(driver, racingClass)
                .orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_TRANSFER_ERROR));
    }

    @Override
    public List<ConstructorDriverRelation> findConstructorDriverRelationByDriver(Driver driver) {
        return relationRepository.findConstructorDriverRelationByDriver(driver);
    }

    @Override
    public List<ConstructorDriverRelation> findConstructorDriverRelationByConstructor(Constructor constructor) {
        return relationRepository.findConstructorDriverRelationByConstructor(constructor);
    }

    @Override
    public void delete(ConstructorDriverRelation relation) {
        relationRepository.delete(relation);
    }
}
