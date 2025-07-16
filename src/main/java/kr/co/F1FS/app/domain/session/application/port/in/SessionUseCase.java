package kr.co.F1FS.app.domain.session.application.port.in;

import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;

public interface SessionUseCase {
    void save(GrandPrix grandPrix);
}
