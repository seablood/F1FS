package kr.co.F1FS.app.domain.session.application.port.out;

import kr.co.F1FS.app.domain.session.domain.Session;

public interface SessionJpaPort {
    Session save(Session session);
    Session saveAndFlush(Session session);
    Session findById(Long id);
}
