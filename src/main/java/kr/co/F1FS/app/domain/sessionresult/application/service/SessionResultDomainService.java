package kr.co.F1FS.app.domain.sessionresult.application.service;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.session.domain.Session;
import kr.co.F1FS.app.domain.sessionresult.application.mapper.SessionResultMapper;
import kr.co.F1FS.app.domain.sessionresult.domain.SessionResult;
import kr.co.F1FS.app.domain.sessionresult.presentation.dto.CreateSessionResultCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionResultDomainService {
    private final SessionResultMapper sessionResultMapper;

    public SessionResult createEntity(CreateSessionResultCommand command, Session session, Driver driver,
                                      Constructor constructor){
        return sessionResultMapper.toSessionResult(command, session, driver, constructor);
    }
}
