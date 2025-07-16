package kr.co.F1FS.app.domain.sessionresult.application.mapper;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.session.domain.Session;
import kr.co.F1FS.app.domain.sessionresult.domain.SessionResult;
import kr.co.F1FS.app.domain.sessionresult.presentation.dto.CreateSessionResultCommand;
import org.springframework.stereotype.Component;

@Component
public class SessionResultMapper {
    public SessionResult toSessionResult(CreateSessionResultCommand command, Session session, Driver driver,
                                         Constructor constructor){
        return SessionResult.builder()
                .session(session)
                .driver(driver)
                .constructor(constructor)
                .position(command.getPosition())
                .timeOrGap(command.getTimeOrGap())
                .points(command.getPoints())
                .isFastestLap(command.isFastestLap())
                .raceStatus(command.getRaceStatus())
                .build();
    }
}
