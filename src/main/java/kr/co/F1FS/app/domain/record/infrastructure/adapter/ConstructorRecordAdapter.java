package kr.co.F1FS.app.domain.record.infrastructure.adapter;

import kr.co.F1FS.app.domain.constructor.application.port.out.ConstructorRecordPort;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import kr.co.F1FS.app.domain.record.infrastructure.repository.CurrentSeasonRepository;
import kr.co.F1FS.app.domain.record.infrastructure.repository.SinceDebutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConstructorRecordAdapter implements ConstructorRecordPort {
    private final CurrentSeasonRepository currentSeasonRepository;
    private final SinceDebutRepository sinceDebutRepository;

    @Override
    public void save(CurrentSeason currentSeason, SinceDebut sinceDebut) {
        currentSeasonRepository.save(currentSeason);
        sinceDebutRepository.save(sinceDebut);
    }

    @Override
    public void saveAndFlush(CurrentSeason currentSeason) {
        currentSeasonRepository.saveAndFlush(currentSeason);
    }
}
