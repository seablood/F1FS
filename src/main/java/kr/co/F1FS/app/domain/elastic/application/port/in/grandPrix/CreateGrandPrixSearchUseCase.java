package kr.co.F1FS.app.domain.elastic.application.port.in.grandPrix;

import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;

public interface CreateGrandPrixSearchUseCase {
    void save(GrandPrix grandPrix);
}
