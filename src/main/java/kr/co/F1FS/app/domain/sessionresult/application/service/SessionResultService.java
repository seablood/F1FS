package kr.co.F1FS.app.domain.sessionresult.application.service;

import kr.co.F1FS.app.domain.constructor.application.port.in.ConstructorRecordRelationUseCase;
import kr.co.F1FS.app.domain.constructor.application.port.in.ConstructorUseCase;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.application.port.in.DriverRecordRelationUseCase;
import kr.co.F1FS.app.domain.driver.application.port.in.DriverUseCase;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.session.application.port.in.SessionUseCase;
import kr.co.F1FS.app.domain.session.domain.Session;
import kr.co.F1FS.app.domain.sessionresult.application.mapper.SessionResultMapper;
import kr.co.F1FS.app.domain.sessionresult.application.port.in.SessionResultUseCase;
import kr.co.F1FS.app.domain.sessionresult.application.port.out.SessionResultJpaPort;
import kr.co.F1FS.app.domain.sessionresult.domain.SessionResult;
import kr.co.F1FS.app.domain.sessionresult.presentation.dto.CreateSessionResultCommand;
import kr.co.F1FS.app.global.presentation.dto.sessionresult.ResponseSessionResultDTO;
import kr.co.F1FS.app.global.util.CacheEvictUtil;
import kr.co.F1FS.app.global.util.RaceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionResultService implements SessionResultUseCase {
    private final DriverUseCase driverUseCase;
    private final ConstructorUseCase constructorUseCase;
    private final DriverRecordRelationUseCase driverRecordRelationUseCase;
    private final ConstructorRecordRelationUseCase constructorRecordRelationUseCase;
    private final SessionUseCase sessionUseCase;
    private final SessionResultJpaPort sessionResultJpaPort;
    private final SessionResultMapper sessionResultMapper;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    public void save(List<CreateSessionResultCommand> commandList, Long id, String racingClassCode){
        Session session = sessionUseCase.findByIdNotDTONotCache(id);
        switch (session.getSessionType()){
            case PRACTICE_1, PRACTICE_2, PRACTICE_3 -> {
                for (CreateSessionResultCommand command : commandList){
                    Driver driver = driverUseCase.findByNameNotDTONotCache(command.getDriverName());
                    Constructor constructor = constructorUseCase.findByNameNotDTONotCache(command.getConstructorName());

                    SessionResult sessionResult = sessionResultMapper.toSessionResult(command, session, driver, constructor);

                    sessionResultJpaPort.save(sessionResult);
                }
            }
            case SPRINT_RACE, RACE -> {
                for (CreateSessionResultCommand command : commandList){
                    Driver driver = driverUseCase.findByNameNotDTONotCache(command.getDriverName());
                    Constructor constructor = constructorUseCase.findByNameNotDTONotCache(command.getConstructorName());
                    if(command.getRaceStatus().equals(RaceStatus.FINISHED)){
                        cacheEvictUtil.evictCachingDriver(driver);
                        cacheEvictUtil.evictCachingConstructor(constructor);
                        cacheEvictUtil.evictCachingStandingList(session.getRacingClass().toString());

                        driverUseCase.updateRecordForRace(driver, command.getPosition(), command.getPoints(), command.isFastestLap());
                        constructorUseCase.updateRecordForRace(constructor, command.getPosition(), command.getPoints(),
                                command.isFastestLap());
                    }

                    SessionResult sessionResult = sessionResultMapper.toSessionResult(command, session, driver, constructor);

                    sessionResultJpaPort.save(sessionResult);
                }
                driverRecordRelationUseCase.updateChampionshipRank(racingClassCode);
                constructorRecordRelationUseCase.updateChampionshipRank(racingClassCode);
            }
            case SPRINT_QUALIFYING, QUALIFYING -> {
                for (CreateSessionResultCommand command : commandList){
                    Driver driver = driverUseCase.findByNameNotDTONotCache(command.getDriverName());
                    Constructor constructor = constructorUseCase.findByNameNotDTONotCache(command.getConstructorName());
                    if(command.getRaceStatus().equals(RaceStatus.FINISHED)){
                        cacheEvictUtil.evictCachingDriver(driver);
                        cacheEvictUtil.evictCachingConstructor(constructor);

                        driverUseCase.updateRecordForQualifying(driver, command.getPosition());
                        constructorUseCase.updateRecordForQualifying(constructor, command.getPosition());
                    }

                    SessionResult sessionResult = sessionResultMapper.toSessionResult(command, session, driver, constructor);

                    sessionResultJpaPort.save(sessionResult);
                }
            }
        }
    }

    @Override
    public List<ResponseSessionResultDTO> getSessionResultBySession(Session session){
        List<SessionResult> resultList = sessionResultJpaPort.findSessionResultsBySession(session);
        List<ResponseSessionResultDTO> dtoList = resultList.stream()
                .map(sessionResult -> sessionResultMapper.toResponseSessionResultDTO(sessionResult))
                .sorted(Comparator.comparingInt(ResponseSessionResultDTO::getPosition))
                .toList();

        return dtoList;
    }
}
