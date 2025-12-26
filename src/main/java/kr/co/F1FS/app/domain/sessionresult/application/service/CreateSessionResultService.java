package kr.co.F1FS.app.domain.sessionresult.application.service;

import kr.co.F1FS.app.domain.constructor.application.port.in.constructor.QueryConstructorUseCase;
import kr.co.F1FS.app.domain.constructor.application.port.in.record.QueryConstructorRecordRelationUseCase;
import kr.co.F1FS.app.domain.constructor.application.port.in.record.UpdateConstructorRecordRelationUseCase;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.domain.ConstructorRecordRelation;
import kr.co.F1FS.app.domain.driver.application.port.in.debut.QueryDriverDebutRelationUseCase;
import kr.co.F1FS.app.domain.driver.application.port.in.driver.QueryDriverUseCase;
import kr.co.F1FS.app.domain.driver.application.port.in.record.QueryDriverRecordRelationUseCase;
import kr.co.F1FS.app.domain.driver.application.port.in.record.UpdateDriverRecordRelationUseCase;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverDebutRelation;
import kr.co.F1FS.app.domain.driver.domain.rdb.DriverRecordRelation;
import kr.co.F1FS.app.domain.record.application.port.in.sinceDebut.UpdateSinceDebutUseCase;
import kr.co.F1FS.app.domain.session.application.port.in.QuerySessionUseCase;
import kr.co.F1FS.app.domain.session.domain.Session;
import kr.co.F1FS.app.domain.sessionresult.application.port.in.CreateSessionResultUseCase;
import kr.co.F1FS.app.domain.sessionresult.application.port.out.SessionResultJpaPort;
import kr.co.F1FS.app.domain.sessionresult.domain.SessionResult;
import kr.co.F1FS.app.domain.sessionresult.presentation.dto.CreateSessionResultCommand;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.global.util.RaceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateSessionResultService implements CreateSessionResultUseCase {
    private final SessionResultJpaPort sessionResultJpaPort;
    private final SessionResultDomainService sessionResultDomainService;
    private final QuerySessionUseCase querySessionUseCase;
    private final QueryDriverUseCase queryDriverUseCase;
    private final UpdateDriverRecordRelationUseCase updateDriverRecordRelationUseCase;
    private final QueryDriverRecordRelationUseCase queryDriverRecordRelationUseCase;
    private final QueryDriverDebutRelationUseCase queryDriverDebutRelationUseCase;
    private final UpdateSinceDebutUseCase updateSinceDebutUseCase;
    private final QueryConstructorUseCase queryConstructorUseCase;
    private final UpdateConstructorRecordRelationUseCase updateConstructorRecordRelationUseCase;
    private final QueryConstructorRecordRelationUseCase queryConstructorRecordRelationUseCase;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    public void save(List<CreateSessionResultCommand> commandList, Long id, String racingClassCode) {
        Session session = querySessionUseCase.findById(id);

        switch (session.getSessionType()){
            case PRACTICE_1, PRACTICE_2, PRACTICE_3 -> {
                for (CreateSessionResultCommand command : commandList){
                    Driver driver = queryDriverUseCase.findByName(command.getDriverName());
                    Constructor constructor = queryConstructorUseCase.findByName(command.getConstructorName());

                    SessionResult sessionResult = sessionResultDomainService.createEntity(command, session, driver, constructor);

                    sessionResultJpaPort.save(sessionResult);
                }
            }
            case SPRINT_RACE, RACE -> {
                for (CreateSessionResultCommand command : commandList){
                    Driver driver = queryDriverUseCase.findByName(command.getDriverName());
                    Constructor constructor = queryConstructorUseCase.findByName(command.getConstructorName());
                    if(command.getRaceStatus().equals(RaceStatus.FINISHED)){
                        cacheEvictUtil.evictCachingDriver(driver);
                        cacheEvictUtil.evictCachingConstructor(constructor);
                        cacheEvictUtil.evictCachingStandingList(session.getRacingClass().toString());

                        driverUpdateRecordForRace(driver, command.getPosition(), command.getPoints(), command.isFastestLap(), racingClassCode);
                        constructorUpdateRecordForRace(constructor, command.getPosition(), command.getPoints(),
                                command.isFastestLap(), racingClassCode);
                    }

                    SessionResult sessionResult = sessionResultDomainService.createEntity(command, session, driver, constructor);

                    sessionResultJpaPort.save(sessionResult);
                }
            }
            case SPRINT_QUALIFYING, QUALIFYING -> {
                for (CreateSessionResultCommand command : commandList){
                    Driver driver = queryDriverUseCase.findByName(command.getDriverName());
                    Constructor constructor = queryConstructorUseCase.findByName(command.getConstructorName());
                    if(command.getRaceStatus().equals(RaceStatus.FINISHED)){
                        cacheEvictUtil.evictCachingDriver(driver);
                        cacheEvictUtil.evictCachingConstructor(constructor);

                        driverUpdateRecordForQualifying(driver, command.getPosition());
                        constructorUpdateRecordForQualifying(constructor, command.getPosition());
                    }

                    SessionResult sessionResult = sessionResultDomainService.createEntity(command, session, driver, constructor);

                    sessionResultJpaPort.save(sessionResult);
                }
            }
        }
    }

    public void driverUpdateRecordForRace(Driver driver, int position, int points, boolean isFastestLap, String racingClassCode){
        DriverRecordRelation recordRelation = queryDriverRecordRelationUseCase
                .findDriverRecordRelationByDriverInfoAndRacingClass(driver);
        DriverDebutRelation debutRelation = queryDriverDebutRelationUseCase
                .findDriverDebutRelationByDriverSinceInfoAndRacingClass(driver);

        updateDriverRecordRelationUseCase.updateRecordForRace(recordRelation, position, points, isFastestLap);
        updateSinceDebutUseCase.updateSinceDebutForRace(debutRelation.getSinceDebut(), position, isFastestLap);
        updateDriverRecordRelationUseCase.updateChampionshipRank(racingClassCode);
    }

    public void driverUpdateRecordForQualifying(Driver driver, int position){
        DriverRecordRelation recordRelation = queryDriverRecordRelationUseCase
                .findDriverRecordRelationByDriverInfoAndRacingClass(driver);
        DriverDebutRelation debutRelation = queryDriverDebutRelationUseCase
                .findDriverDebutRelationByDriverSinceInfoAndRacingClass(driver);

        updateDriverRecordRelationUseCase.updateRecordForQualifying(recordRelation, position);
        updateSinceDebutUseCase.updateSinceDebutForQualifying(debutRelation.getSinceDebut(), position);
    }

    public void constructorUpdateRecordForRace(Constructor constructor, int position, int points, boolean isFastestLap, String racingClassCode){
        ConstructorRecordRelation relation = queryConstructorRecordRelationUseCase.findByConstructor(constructor);

        updateConstructorRecordRelationUseCase.updateRecordForRace(relation, position, points, isFastestLap);
        updateConstructorRecordRelationUseCase.updateChampionshipRank(racingClassCode);
    }

    public void constructorUpdateRecordForQualifying(Constructor constructor, int position){
        ConstructorRecordRelation relation = queryConstructorRecordRelationUseCase.findByConstructor(constructor);

        updateConstructorRecordRelationUseCase.updateRecordForQualifying(relation, position);
    }
}
