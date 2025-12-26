package kr.co.F1FS.app.domain.session.application.port.in;

import kr.co.F1FS.app.domain.session.domain.Session;

public interface QuerySessionUseCase {
    Session findById(Long id);
}
