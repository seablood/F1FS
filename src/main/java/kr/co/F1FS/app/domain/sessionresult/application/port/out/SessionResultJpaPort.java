package kr.co.F1FS.app.domain.sessionresult.application.port.out;

import kr.co.F1FS.app.domain.session.domain.Session;
import kr.co.F1FS.app.domain.sessionresult.domain.SessionResult;

import java.util.List;

public interface SessionResultJpaPort {
    SessionResult save(SessionResult sessionResult);
    SessionResult saveAndFlush(SessionResult sessionResult);
    List<SessionResult> findSessionResultsBySession(Session session);
}
