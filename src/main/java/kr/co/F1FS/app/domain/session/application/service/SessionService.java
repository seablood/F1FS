package kr.co.F1FS.app.domain.session.application.service;

import kr.co.F1FS.app.domain.grandprix.application.port.in.GrandPrixUseCase;
import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import kr.co.F1FS.app.domain.session.application.mapper.SessionMapper;
import kr.co.F1FS.app.domain.session.application.port.in.SessionUseCase;
import kr.co.F1FS.app.domain.session.domain.Session;
import kr.co.F1FS.app.domain.session.infrastructure.repository.SessionRepository;
import kr.co.F1FS.app.domain.sessionresult.application.port.in.SessionResultUseCase;
import kr.co.F1FS.app.global.presentation.dto.session.ResponseSessionDTO;
import kr.co.F1FS.app.global.presentation.dto.sessionresult.ResponseSessionResultDTO;
import kr.co.F1FS.app.global.util.SessionType;
import kr.co.F1FS.app.global.util.exception.session.SessionException;
import kr.co.F1FS.app.global.util.exception.session.SessionExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService implements SessionUseCase {
    private final GrandPrixUseCase grandPrixUseCase;
    private final SessionResultUseCase sessionResultUseCase;
    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;

    private static final SessionType[] sessionList = {SessionType.PRACTICE_1, SessionType.PRACTICE_2,
            SessionType.PRACTICE_3, SessionType.SPRINT_QUALIFYING, SessionType.QUALIFYING, SessionType.SPRINT_RACE,
            SessionType.RACE};

    @Override
    public void save(GrandPrix grandPrix) {
        for (SessionType sessionType : sessionList){
            isSession(sessionType, grandPrix);
        }
    }

    @Override
    @Cacheable(value = "SessionDTO", key = "#id", cacheManager = "redisLongCacheManager")
    public ResponseSessionDTO getSessionByID(Long id){
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new SessionException(SessionExceptionType.SESSION_NOT_FOUND));
        List<ResponseSessionResultDTO> resultList = sessionResultUseCase.getSessionResultBySession(session);

        return sessionMapper.toResponseSessionDTO(session, resultList);
    }

    public void isSession(SessionType sessionType, GrandPrix grandPrix){
        switch (sessionType){
            case PRACTICE_1 -> {
                if (grandPrix.getFirstPracticeTime() != null) {
                    Session session = sessionMapper.toSession(sessionType, grandPrix.getFirstPracticeTime(),
                            grandPrix.getRacingClass());
                    session = sessionRepository.save(session);
                    grandPrixUseCase.setFirstPractice(grandPrix, session.getId());
                }
            }
            case PRACTICE_2 -> {
                if (grandPrix.getSecondPracticeTime() != null) {
                    Session session = sessionMapper.toSession(sessionType, grandPrix.getSecondPracticeTime(),
                            grandPrix.getRacingClass());
                    session = sessionRepository.save(session);
                    grandPrixUseCase.setSecondPractice(grandPrix, session.getId());
                }
            }
            case PRACTICE_3 -> {
                if (grandPrix.getThirdPracticeTime() != null) {
                    Session session = sessionMapper.toSession(sessionType, grandPrix.getThirdPracticeTime(),
                            grandPrix.getRacingClass());
                    session = sessionRepository.save(session);
                    grandPrixUseCase.setThirdPractice(grandPrix, session.getId());
                }
            }
            case SPRINT_QUALIFYING -> {
                if (grandPrix.getSprintQualifyingTime() != null) {
                    Session session = sessionMapper.toSession(sessionType, grandPrix.getSprintQualifyingTime(),
                            grandPrix.getRacingClass());
                    session = sessionRepository.save(session);
                    grandPrixUseCase.setSprintQualifying(grandPrix, session.getId());
                }
            }
            case QUALIFYING -> {
                if (grandPrix.getQualifyingTime() != null) {
                    Session session = sessionMapper.toSession(sessionType, grandPrix.getQualifyingTime(),
                            grandPrix.getRacingClass());
                    session = sessionRepository.save(session);
                    grandPrixUseCase.setQualifying(grandPrix, session.getId());
                }
            }
            case SPRINT_RACE -> {
                if (grandPrix.getSprintTime() != null) {
                    Session session = sessionMapper.toSession(sessionType, grandPrix.getSprintTime(),
                            grandPrix.getRacingClass());
                    session = sessionRepository.save(session);
                    grandPrixUseCase.setSprint(grandPrix, session.getId());
                }
            }
            case RACE -> {
                if (grandPrix.getRaceTime() != null) {
                    Session session = sessionMapper.toSession(sessionType, grandPrix.getRaceTime(),
                            grandPrix.getRacingClass());
                    session = sessionRepository.save(session);
                    grandPrixUseCase.setRace(grandPrix, session.getId());
                }
            }
            default -> {
                return;
            }
        }
    }
}
