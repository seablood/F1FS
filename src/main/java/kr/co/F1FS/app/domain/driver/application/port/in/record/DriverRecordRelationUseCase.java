package kr.co.F1FS.app.domain.driver.application.port.in.record;

import kr.co.F1FS.app.domain.driver.presentation.dto.ResponseDriverStandingDTO;

import java.util.List;

public interface DriverRecordRelationUseCase {
    List<ResponseDriverStandingDTO> getDriverStandingList(String racingClassCode);
}
