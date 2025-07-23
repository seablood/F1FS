package kr.co.F1FS.app.domain.sessionresult.application.service;

import kr.co.F1FS.app.domain.constructor.application.port.in.ConstructorUseCase;
import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.application.port.in.DriverUseCase;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.session.domain.Session;
import kr.co.F1FS.app.domain.sessionresult.application.mapper.SessionResultMapper;
import kr.co.F1FS.app.domain.sessionresult.application.port.in.SessionResultUseCase;
import kr.co.F1FS.app.domain.sessionresult.application.port.out.SessionResultConstructorPort;
import kr.co.F1FS.app.domain.sessionresult.application.port.out.SessionResultDriverPort;
import kr.co.F1FS.app.domain.sessionresult.application.port.out.SessionResultSessionPort;
import kr.co.F1FS.app.domain.sessionresult.domain.SessionResult;
import kr.co.F1FS.app.domain.sessionresult.infrastructure.repository.SessionResultRepository;
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
    private final SessionResultConstructorPort constructorPort;
    private final DriverUseCase driverUseCase;
    private final ConstructorUseCase constructorUseCase;
    private final SessionResultDriverPort driverPort;
    private final SessionResultSessionPort sessionPort;
    private final SessionResultRepository sessionResultRepository;
    private final SessionResultMapper sessionResultMapper;
    private final CacheEvictUtil cacheEvictUtil;

    @Override
    public void save(List<CreateSessionResultCommand> commandList, Long id){
        Session session = sessionPort.getSessionByIdNotDTO(id);
        switch (session.getSessionType()){
            case PRACTICE_1, PRACTICE_2, PRACTICE_3 -> {
                for (CreateSessionResultCommand command : commandList){
                    Driver driver = driverPort.getDriverByNameNotDTO(command.getDriverName());
                    Constructor constructor = constructorPort.getConstructorByNameNotDTO(command.getConstructorName());

                    SessionResult sessionResult = sessionResultMapper.toSessionResult(command, session, driver, constructor);

                    sessionResultRepository.save(sessionResult);
                }
            }
            case SPRINT_RACE, RACE -> {
                for (CreateSessionResultCommand command : commandList){
                    Driver driver = driverPort.getDriverByNameNotDTO(command.getDriverName());
                    Constructor constructor = constructorPort.getConstructorByNameNotDTO(command.getConstructorName());
                    if(command.getRaceStatus().equals(RaceStatus.FINISHED)){
                        cacheEvictUtil.evictCachingDriver(driver);
                        cacheEvictUtil.evictCachingConstructor(constructor);

                        driverUseCase.updateRecordForRace(driver, command.getPosition(), command.getPoints(), command.isFastestLap());
                        constructorUseCase.updateRecordForRace(constructor, command.getPosition(), command.getPoints(),
                                command.isFastestLap());
                    }

                    SessionResult sessionResult = sessionResultMapper.toSessionResult(command, session, driver, constructor);

                    sessionResultRepository.save(sessionResult);
                }
            }
            case SPRINT_QUALIFYING, QUALIFYING -> {
                for (CreateSessionResultCommand command : commandList){
                    Driver driver = driverPort.getDriverByNameNotDTO(command.getDriverName());
                    Constructor constructor = constructorPort.getConstructorByNameNotDTO(command.getConstructorName());
                    if(command.getRaceStatus().equals(RaceStatus.FINISHED)){
                        cacheEvictUtil.evictCachingDriver(driver);
                        cacheEvictUtil.evictCachingConstructor(constructor);

                        driverUseCase.updateRecordForQualifying(driver, command.getPosition());
                        constructorUseCase.updateRecordForQualifying(constructor, command.getPosition());
                    }

                    SessionResult sessionResult = sessionResultMapper.toSessionResult(command, session, driver, constructor);

                    sessionResultRepository.save(sessionResult);
                }
            }
        }
    }

    @Override
    public List<ResponseSessionResultDTO> getSessionResultBySession(Session session){
        List<SessionResult> resultList = sessionResultRepository.findSessionResultsBySession(session);
        List<ResponseSessionResultDTO> dtoList = resultList.stream()
                .map(sessionResult -> sessionResultMapper.toResponseSessionResultDTO(sessionResult))
                .sorted(Comparator.comparingInt(ResponseSessionResultDTO::getPosition))
                .toList();

        return dtoList;
    }
}
