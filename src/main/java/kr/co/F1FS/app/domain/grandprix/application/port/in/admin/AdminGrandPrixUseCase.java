package kr.co.F1FS.app.domain.grandprix.application.port.in.admin;

import kr.co.F1FS.app.domain.grandprix.presentation.dto.admin.CreateGrandPrixDTO;
import kr.co.F1FS.app.domain.grandprix.presentation.dto.admin.ModifyGrandPrixDTO;
import kr.co.F1FS.app.global.presentation.dto.grandprix.ResponseGrandPrixDTO;
import kr.co.F1FS.app.global.presentation.dto.grandprix.SimpleResponseGrandPrixDTO;

import java.util.List;

public interface AdminGrandPrixUseCase {
    ResponseGrandPrixDTO save(CreateGrandPrixDTO dto);
    List<SimpleResponseGrandPrixDTO> findAll(Integer season);
    ResponseGrandPrixDTO getGrandPrixById(Long id);
    ResponseGrandPrixDTO modify(ModifyGrandPrixDTO dto, Long id);
}
