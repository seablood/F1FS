package kr.co.F1FS.app.domain.grandprix.application.service;

import kr.co.F1FS.app.domain.grandprix.application.mapper.GrandPrixMapper;
import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import kr.co.F1FS.app.domain.grandprix.presentation.dto.CreateGrandPrixCommand;
import kr.co.F1FS.app.domain.grandprix.presentation.dto.ModifyGrandPrixCommand;
import kr.co.F1FS.app.global.application.service.ValidationService;
import kr.co.F1FS.app.global.util.SessionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GrandPrixDomainService {
    private final GrandPrixMapper grandPrixMapper;
    private final ValidationService validationService;

    public GrandPrix createEntity(CreateGrandPrixCommand command){
        GrandPrix grandPrix = grandPrixMapper.toGrandPrix(command);
        validationService.checkValid(grandPrix);

        return grandPrix;
    }

    public void modify(ModifyGrandPrixCommand command, GrandPrix grandPrix){
        grandPrix.modify(command);
    }

    public void setSessionId(GrandPrix grandPrix, Long sessionId, SessionType sessionType){
        switch (sessionType){
            case PRACTICE_1 -> grandPrix.setFirstPractice(sessionId);
            case PRACTICE_2 -> grandPrix.setSecondPractice(sessionId);
            case PRACTICE_3 -> grandPrix.setThirdPractice(sessionId);
            case SPRINT_QUALIFYING -> grandPrix.setSprintQualifying(sessionId);
            case SPRINT_RACE -> grandPrix.setSprint(sessionId);
            case QUALIFYING -> grandPrix.setQualifying(sessionId);
            case RACE -> grandPrix.setRace(sessionId);
            default -> {}
        }
    }
}
