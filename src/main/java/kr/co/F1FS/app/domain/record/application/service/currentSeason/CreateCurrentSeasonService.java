package kr.co.F1FS.app.domain.record.application.service.currentSeason;

import kr.co.F1FS.app.domain.record.application.port.in.currentSeason.CreateCurrentSeasonUseCase;
import kr.co.F1FS.app.domain.record.application.port.out.currentSeason.CurrentSeasonJpaPort;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.record.presentation.dto.currentSeason.CreateCurrentSeasonDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCurrentSeasonService implements CreateCurrentSeasonUseCase {
    private final CurrentSeasonJpaPort currentSeasonJpaPort;
    private final CurrentSeasonDomainService currentSeasonDomainService;

    @Override
    public CurrentSeason save(CreateCurrentSeasonDTO currentSeasonDTO) {
        CurrentSeason currentSeason = currentSeasonDomainService.createEntity(currentSeasonDTO);

        return currentSeasonJpaPort.save(currentSeason);
    }

    @Override
    public CurrentSeason save() {
        CurrentSeason currentSeason = currentSeasonDomainService.createEntity();

        return currentSeasonJpaPort.save(currentSeason);
    }
}
