package kr.co.F1FS.app.domain.grandprix.application.port.in;

import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import kr.co.F1FS.app.domain.grandprix.presentation.dto.CreateGrandPrixCommand;
import kr.co.F1FS.app.domain.grandprix.presentation.dto.ModifyGrandPrixCommand;
import kr.co.F1FS.app.global.presentation.dto.grandprix.ResponseGrandPrixDTO;
import kr.co.F1FS.app.global.presentation.dto.grandprix.SimpleResponseGrandPrixDTO;

import java.util.List;

public interface GrandPrixUseCase {
    GrandPrix createGrandPrix(CreateGrandPrixCommand command);
    List<SimpleResponseGrandPrixDTO> findAll(Integer season);
    ResponseGrandPrixDTO getGrandPrixById(Long id);
    GrandPrix modify(ModifyGrandPrixCommand command, GrandPrix grandPrix);
    void setFirstPractice(GrandPrix grandPrix, Long firstPractice);
    void setSecondPractice(GrandPrix grandPrix, Long secondPractice);
    void setThirdPractice(GrandPrix grandPrix, Long thirdPractice);
    void setSprintQualifying(GrandPrix grandPrix, Long sprintQualifying);
    void setQualifying(GrandPrix grandPrix, Long qualifying);
    void setSprint(GrandPrix grandPrix, Long sprint);
    void setRace(GrandPrix grandPrix, Long race);
}
