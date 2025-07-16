package kr.co.F1FS.app.domain.session.application.service;

import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import kr.co.F1FS.app.domain.session.application.mapper.SessionMapper;
import kr.co.F1FS.app.domain.session.application.port.in.SessionUseCase;
import kr.co.F1FS.app.domain.session.domain.Session;
import kr.co.F1FS.app.domain.session.infrastructure.repository.SessionRepository;
import kr.co.F1FS.app.global.util.SessionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionService implements SessionUseCase {
    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;

    private static final SessionType[] sessionList = {SessionType.PRACTICE_1, SessionType.PRACTICE_2,
            SessionType.PRACTICE_3, SessionType.SPRINT_QUALIFYING, SessionType.QUALIFYING, SessionType.SPRINT_RACE,
            SessionType.RACE};

    @Override
    public void save(GrandPrix grandPrix) {
        for (SessionType sessionType : sessionList){
            if(isSession(sessionType, grandPrix)){
                Session session = sessionMapper.toSession(sessionType, grandPrix);
                sessionRepository.save(session);
            }
        }
    }

    public boolean isSession(SessionType sessionType, GrandPrix grandPrix){
        switch (sessionType){
            case PRACTICE_1 -> {
                if (grandPrix.getFirstPracticeTime() != null) return true;
                else return false;
            }
            case PRACTICE_2 -> {
                if (grandPrix.getSecondPracticeTime() != null) return true;
                else return false;
            }
            case PRACTICE_3 -> {
                if (grandPrix.getThirdPracticeTime() != null) return true;
                else return false;
            }
            case SPRINT_QUALIFYING -> {
                if (grandPrix.getSprintQualifyingTime() != null) return true;
                else return false;
            }
            case QUALIFYING -> {
                if (grandPrix.getQualifyingTime() != null) return true;
                else return false;
            }
            case SPRINT_RACE -> {
                if (grandPrix.getSprintTime() != null) return true;
                else return false;
            }
            case RACE -> {
                if (grandPrix.getRaceTime() != null) return true;
                else return false;
            }
            default -> {
                return false;
            }
        }
    }
}
