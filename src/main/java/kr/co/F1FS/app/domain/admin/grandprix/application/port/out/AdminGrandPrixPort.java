package kr.co.F1FS.app.domain.admin.grandprix.application.port.out;

import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import kr.co.F1FS.app.global.presentation.dto.grandprix.SimpleResponseGrandPrixDTO;

import java.util.List;

public interface AdminGrandPrixPort {
    GrandPrix save(GrandPrix grandPrix);
    void saveAndFlush(GrandPrix grandPrix);
    List<SimpleResponseGrandPrixDTO> findAll(Integer season);
    GrandPrix getGrandPrixById(Long id);
}
