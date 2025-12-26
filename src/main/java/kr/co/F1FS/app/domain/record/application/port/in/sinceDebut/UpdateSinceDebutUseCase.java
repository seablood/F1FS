package kr.co.F1FS.app.domain.record.application.port.in.sinceDebut;

import kr.co.F1FS.app.domain.record.domain.SinceDebut;

public interface UpdateSinceDebutUseCase {
    void updateSinceDebutForRace(SinceDebut sinceDebut, int position, boolean isFastestLap);
    void updateSinceDebutForQualifying(SinceDebut sinceDebut, int position);
}
