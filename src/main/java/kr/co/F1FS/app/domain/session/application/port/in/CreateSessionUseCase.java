package kr.co.F1FS.app.domain.session.application.port.in;

import kr.co.F1FS.app.domain.grandprix.domain.GrandPrix;

public interface CreateSessionUseCase {
    void save(GrandPrix grandPrix);
}
