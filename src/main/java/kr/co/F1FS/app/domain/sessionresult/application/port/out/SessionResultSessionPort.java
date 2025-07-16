package kr.co.F1FS.app.domain.sessionresult.application.port.out;

import kr.co.F1FS.app.domain.session.domain.Session;

public interface SessionResultSessionPort {
    Session getSessionByIdNotDTO(Long id);
}
