package kr.co.F1FS.app.domain.admin.grandprix.application.port.out;

import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;

public interface AdminGrandPrixPort {
    GrandPrix save(GrandPrix grandPrix);
    void saveAndFlush(GrandPrix grandPrix);
    GrandPrix getGrandPrixById(Long id);
}
