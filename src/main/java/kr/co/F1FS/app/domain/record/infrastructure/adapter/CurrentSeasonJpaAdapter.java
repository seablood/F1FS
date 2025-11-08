package kr.co.F1FS.app.domain.record.infrastructure.adapter;

import kr.co.F1FS.app.domain.record.application.port.out.CurrentSeasonJpaPort;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.record.infrastructure.repository.CurrentSeasonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrentSeasonJpaAdapter implements CurrentSeasonJpaPort {
    private final CurrentSeasonRepository currentSeasonRepository;

    @Override
    public CurrentSeason save(CurrentSeason currentSeason) {
        return currentSeasonRepository.save(currentSeason);
    }

    @Override
    public CurrentSeason saveAndFlush(CurrentSeason currentSeason) {
        return currentSeasonRepository.saveAndFlush(currentSeason);
    }
}
