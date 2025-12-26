package kr.co.F1FS.app.domain.grandprix.application.port.in;

import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import kr.co.F1FS.app.domain.grandprix.presentation.dto.ModifyGrandPrixCommand;
import kr.co.F1FS.app.global.util.SessionType;

public interface UpdateGrandPrixUseCase {
    void modify(ModifyGrandPrixCommand command, GrandPrix grandPrix);
    void setSessionPart(GrandPrix grandPrix, Long sessionId, SessionType sessionType);
}
