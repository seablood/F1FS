package kr.co.F1FS.app.domain.grandprix.application.port.in;

import kr.co.F1FS.app.global.presentation.dto.grandprix.ResponseGrandPrixDTO;
import kr.co.F1FS.app.global.presentation.dto.grandprix.SimpleResponseGrandPrixDTO;

import java.util.List;

public interface GrandPrixUseCase {
    List<SimpleResponseGrandPrixDTO> findAll(Integer season);
    ResponseGrandPrixDTO getGrandPrixById(Long id);
}
