package kr.co.F1FS.app.domain.session.application.service;

import kr.co.F1FS.app.domain.session.application.mapper.SessionMapper;
import kr.co.F1FS.app.domain.session.domain.Session;
import kr.co.F1FS.app.global.util.RacingClass;
import kr.co.F1FS.app.global.util.SessionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionDomainService {
    private final SessionMapper sessionMapper;

    public Session createEntity(SessionType sessionType, String sessionTime, RacingClass racingClass){
        return sessionMapper.toSession(sessionType, sessionTime, racingClass);
    }
}
