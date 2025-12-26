package kr.co.F1FS.app.domain.session.application.service;

import kr.co.F1FS.app.domain.grandprix.application.port.in.UpdateGrandPrixUseCase;
import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import kr.co.F1FS.app.domain.session.application.port.in.CreateSessionUseCase;
import kr.co.F1FS.app.domain.session.application.port.out.SessionJpaPort;
import kr.co.F1FS.app.domain.session.domain.Session;
import kr.co.F1FS.app.global.util.SessionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateSessionService implements CreateSessionUseCase {
    private final SessionJpaPort sessionJpaPort;
    private final SessionDomainService sessionDomainService;
    private final UpdateGrandPrixUseCase updateGrandPrixUseCase;

    private static final SessionType[] sessionList = {SessionType.PRACTICE_1, SessionType.PRACTICE_2,
            SessionType.PRACTICE_3, SessionType.SPRINT_QUALIFYING, SessionType.QUALIFYING, SessionType.SPRINT_RACE,
            SessionType.RACE};

    @Override
    public void save(GrandPrix grandPrix) {
        for (SessionType sessionType : sessionList){
            isSession(sessionType, grandPrix);
        }
    }

    public void isSession(SessionType sessionType, GrandPrix grandPrix){
        switch (sessionType){
            case PRACTICE_1 -> {
                if (grandPrix.getFirstPracticeTime() != null) {
                    Session session = sessionDomainService.createEntity(sessionType, grandPrix.getFirstPracticeTime(),
                            grandPrix.getRacingClass());
                    session = sessionJpaPort.save(session);
                    updateGrandPrixUseCase.setSessionPart(grandPrix, session.getId(), sessionType);
                }
            }
            case PRACTICE_2 -> {
                if (grandPrix.getSecondPracticeTime() != null) {
                    Session session = sessionDomainService.createEntity(sessionType, grandPrix.getSecondPracticeTime(),
                            grandPrix.getRacingClass());
                    session = sessionJpaPort.save(session);
                    updateGrandPrixUseCase.setSessionPart(grandPrix, session.getId(), sessionType);
                }
            }
            case PRACTICE_3 -> {
                if (grandPrix.getThirdPracticeTime() != null) {
                    Session session = sessionDomainService.createEntity(sessionType, grandPrix.getThirdPracticeTime(),
                            grandPrix.getRacingClass());
                    session = sessionJpaPort.save(session);
                    updateGrandPrixUseCase.setSessionPart(grandPrix, session.getId(), sessionType);
                }
            }
            case SPRINT_QUALIFYING -> {
                if (grandPrix.getSprintQualifyingTime() != null) {
                    Session session = sessionDomainService.createEntity(sessionType, grandPrix.getSprintQualifyingTime(),
                            grandPrix.getRacingClass());
                    session = sessionJpaPort.save(session);
                    updateGrandPrixUseCase.setSessionPart(grandPrix, session.getId(), sessionType);
                }
            }
            case SPRINT_RACE -> {
                if (grandPrix.getSprintTime() != null) {
                    Session session = sessionDomainService.createEntity(sessionType, grandPrix.getSprintTime(),
                            grandPrix.getRacingClass());
                    session = sessionJpaPort.save(session);
                    updateGrandPrixUseCase.setSessionPart(grandPrix, session.getId(), sessionType);
                }
            }
            case QUALIFYING -> {
                if (grandPrix.getQualifyingTime() != null) {
                    Session session = sessionDomainService.createEntity(sessionType, grandPrix.getQualifyingTime(),
                            grandPrix.getRacingClass());
                    session = sessionJpaPort.save(session);
                    updateGrandPrixUseCase.setSessionPart(grandPrix, session.getId(), sessionType);
                }
            }
            case RACE -> {
                if (grandPrix.getRaceTime() != null) {
                    Session session = sessionDomainService.createEntity(sessionType, grandPrix.getRaceTime(),
                            grandPrix.getRacingClass());
                    session = sessionJpaPort.save(session);
                    updateGrandPrixUseCase.setSessionPart(grandPrix, session.getId(), sessionType);
                }
            }
            default -> {}
        }
    }
}
