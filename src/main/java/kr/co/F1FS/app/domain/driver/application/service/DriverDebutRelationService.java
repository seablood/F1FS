package kr.co.F1FS.app.domain.driver.application.service;

import kr.co.F1FS.app.domain.driver.application.mapper.DriverDebutRelationMapper;
import kr.co.F1FS.app.domain.driver.application.port.in.DriverDebutRelationUseCase;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverDebutRelation;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import kr.co.F1FS.app.domain.driver.infrastructure.repository.DriverDebutRelationRepository;
import kr.co.F1FS.app.global.util.exception.driver.DriverException;
import kr.co.F1FS.app.global.util.exception.driver.DriverExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverDebutRelationService implements DriverDebutRelationUseCase {
    private final DriverDebutRelationMapper relationMapper;
    private final DriverDebutRelationRepository debutRelationRepository;

    public void save(Driver driver, SinceDebut sinceDebut){
        DriverDebutRelation debutRelation = relationMapper.toDriverDebutRelation(driver, sinceDebut);

        debutRelationRepository.save(debutRelation);
    }

    public DriverDebutRelation getSinceDebutByDriverAndRacingClass(Driver driver){
        return debutRelationRepository.findDriverDebutRelationByDriverSinceInfoAndRacingClass(driver, driver.getRacingClass())
                .orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_DEBUT_ERROR));
    }
}
