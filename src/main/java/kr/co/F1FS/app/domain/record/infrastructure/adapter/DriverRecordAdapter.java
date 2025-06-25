package kr.co.F1FS.app.domain.record.infrastructure.adapter;

import kr.co.F1FS.app.domain.driver.application.port.out.DriverRecordPort;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import kr.co.F1FS.app.domain.record.infrastructure.repository.CurrentSeasonRepository;
import kr.co.F1FS.app.domain.record.infrastructure.repository.SinceDebutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DriverRecordAdapter implements DriverRecordPort {
    private final CurrentSeasonRepository currentSeasonRepository;
    private final SinceDebutRepository sinceDebutRepository;

    @Override
    public void save(CurrentSeason currentSeason, SinceDebut sinceDebut) {
        currentSeasonRepository.save(currentSeason);
        sinceDebutRepository.save(sinceDebut);
    }
}
