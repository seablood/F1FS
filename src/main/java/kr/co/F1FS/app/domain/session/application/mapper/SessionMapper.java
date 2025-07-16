package kr.co.F1FS.app.domain.session.application.mapper;

import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import kr.co.F1FS.app.domain.session.domain.Session;
import kr.co.F1FS.app.global.util.SessionType;
import org.springframework.stereotype.Component;

@Component
public class SessionMapper {
    public Session toSession(SessionType sessionType, GrandPrix grandPrix){
        return Session.builder()
                .sessionType(sessionType)
                .grandPrix(grandPrix)
                .build();
    }
}
