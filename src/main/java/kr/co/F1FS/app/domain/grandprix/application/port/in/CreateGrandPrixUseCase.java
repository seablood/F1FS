package kr.co.F1FS.app.domain.grandprix.application.port.in;

import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import kr.co.F1FS.app.domain.grandprix.presentation.dto.CreateGrandPrixCommand;

public interface CreateGrandPrixUseCase {
    GrandPrix save(CreateGrandPrixCommand command);
}
