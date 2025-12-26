package kr.co.F1FS.app.domain.record.application.port.in.sinceDebut;

import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import kr.co.F1FS.app.domain.record.presentation.dto.sinceDebut.CreateSinceDebutDTO;

public interface CreateSinceDebutUseCase {
    SinceDebut save(CreateSinceDebutDTO sinceDebutDTO);
    SinceDebut save();
}
