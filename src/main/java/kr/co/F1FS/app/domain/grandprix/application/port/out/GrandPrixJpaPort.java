package kr.co.F1FS.app.domain.grandprix.application.port.out;

import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;
import kr.co.F1FS.app.global.presentation.dto.grandprix.SimpleResponseGrandPrixDTO;

import java.util.List;

public interface GrandPrixJpaPort {
    GrandPrix save(GrandPrix grandPrix);
    GrandPrix saveAndFlush(GrandPrix grandPrix);
    List<GrandPrix> findGrandPrixesBySeason(Integer season);
    GrandPrix findById(Long id);
}
