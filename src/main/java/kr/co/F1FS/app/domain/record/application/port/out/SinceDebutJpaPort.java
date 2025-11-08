package kr.co.F1FS.app.domain.record.application.port.out;

import kr.co.F1FS.app.domain.record.domain.SinceDebut;

public interface SinceDebutJpaPort {
    SinceDebut save(SinceDebut sinceDebut);
    SinceDebut saveAndFlush(SinceDebut sinceDebut);
}
