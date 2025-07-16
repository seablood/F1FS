package kr.co.F1FS.app.domain.admin.grandprix.application.port.in;

import kr.co.F1FS.app.domain.admin.grandprix.presentation.dto.CreateGrandPrixDTO;
import kr.co.F1FS.app.domain.admin.grandprix.presentation.dto.ModifyGrandPrixDTO;
import kr.co.F1FS.app.global.presentation.dto.grandprix.ResponseGrandPrixDTO;

public interface AdminGrandPrixUseCase {
    ResponseGrandPrixDTO save(CreateGrandPrixDTO dto);
    ResponseGrandPrixDTO modify(ModifyGrandPrixDTO dto, Long id);
}
