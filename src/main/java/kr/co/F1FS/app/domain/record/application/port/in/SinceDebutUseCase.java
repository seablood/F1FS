package kr.co.F1FS.app.domain.record.application.port.in;

import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import kr.co.F1FS.app.domain.record.presentation.dto.CreateSinceDebutDTO;
import kr.co.F1FS.app.global.presentation.dto.record.ResponseSinceDebutDTO;

public interface SinceDebutUseCase {
    SinceDebut save(SinceDebut sinceDebut);
    SinceDebut saveAndFlush(SinceDebut sinceDebut);
    void updateSinceDebutForRace(SinceDebut sinceDebut, int position, boolean isFastestLap);
    void updateSinceDebutForQualifying(SinceDebut sinceDebut, int position);
    SinceDebut toSinceDebut(CreateSinceDebutDTO dto);
    ResponseSinceDebutDTO toResponseSinceDebutDTO(SinceDebut sinceDebut);
}
