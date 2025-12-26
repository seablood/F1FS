package kr.co.F1FS.app.domain.grandprix.application.port.in;

import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import kr.co.F1FS.app.global.presentation.dto.grandprix.SimpleResponseGrandPrixDTO;

import java.util.List;

public interface QueryGrandPrixUseCase {
    GrandPrix findById(Long id);
    List<SimpleResponseGrandPrixDTO> findAll(Integer season);
}
