package kr.co.F1FS.app.domain.record.application.service;

import kr.co.F1FS.app.domain.record.application.port.in.SinceDebutUseCase;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import kr.co.F1FS.app.domain.record.infrastructure.repository.SinceDebutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SinceDebutService implements SinceDebutUseCase {
    private final SinceDebutRepository sinceDebutRepository;

    @Override
    public void updateSinceDebutForRace(SinceDebut sinceDebut, int position, boolean isFastestLap) {
        sinceDebut.updateEnteredGP();
        if(position <= 3){
            sinceDebut.updatePodiums();
            if(position == 1) sinceDebut.updateHighestFinish();
        }
        if(isFastestLap) sinceDebut.updateFastestLap();

        sinceDebutRepository.saveAndFlush(sinceDebut);
    }

    @Override
    public void updateSinceDebutForQualifying(SinceDebut sinceDebut, int position) {
        if(position == 1) sinceDebut.updatePolePosition();
        sinceDebutRepository.saveAndFlush(sinceDebut);
    }
}
