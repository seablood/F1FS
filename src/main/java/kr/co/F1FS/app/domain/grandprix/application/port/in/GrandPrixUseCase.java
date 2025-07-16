package kr.co.F1FS.app.domain.grandprix.application.port.in;

import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import kr.co.F1FS.app.domain.grandprix.presentation.dto.CreateGrandPrixCommand;
import kr.co.F1FS.app.domain.grandprix.presentation.dto.ModifyGrandPrixCommand;
import kr.co.F1FS.app.global.presentation.dto.grandprix.ResponseGrandPrixDTO;
import kr.co.F1FS.app.global.presentation.dto.grandprix.SimpleResponseGrandPrixDTO;
import org.springframework.data.domain.Page;

public interface GrandPrixUseCase {
    GrandPrix createGrandPrix(CreateGrandPrixCommand command);
    Page<SimpleResponseGrandPrixDTO> findAll(int page, int size);
    ResponseGrandPrixDTO getGrandPrixById(Long id);
    GrandPrix modify(ModifyGrandPrixCommand command, GrandPrix grandPrix);
}
