package kr.co.F1FS.app.domain.record.application.port.in;

import kr.co.F1FS.app.domain.record.domain.SinceDebut;

public interface SinceDebutUseCase {
    void updateSinceDebutForRace(SinceDebut sinceDebut, int position, boolean isFastestLap);
    void updateSinceDebutForQualifying(SinceDebut sinceDebut, int position);
}
