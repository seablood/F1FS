package kr.co.F1FS.app.domain.constructor.application.port.in.record;

import kr.co.F1FS.app.domain.constructor.presentation.dto.record.ResponseConstructorStandingDTO;

import java.util.List;

public interface ConstructorRecordRelationUseCase {
    List<ResponseConstructorStandingDTO> getConstructorStandingList(String racingClassCode);
}
