package kr.co.F1FS.app.domain.record.application.service.sinceDebut;

import kr.co.F1FS.app.domain.record.application.port.in.sinceDebut.UpdateSinceDebutUseCase;
import kr.co.F1FS.app.domain.record.application.port.out.sinceDebut.SinceDebutJpaPort;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateSinceDebutService implements UpdateSinceDebutUseCase {
    private final SinceDebutJpaPort sinceDebutJpaPort;
    private final SinceDebutDomainService sinceDebutDomainService;

    @Override
    public void updateSinceDebutForRace(SinceDebut sinceDebut, int position, boolean isFastestLap) {
        sinceDebutDomainService.updateEnteredGP(sinceDebut);
        if(position <= 3){
            sinceDebutDomainService.updatePodiums(sinceDebut);
            if(position == 1) sinceDebutDomainService.updateHighestFinish(sinceDebut);
        }
        if(isFastestLap) sinceDebutDomainService.updateFastestLap(sinceDebut);

        sinceDebutJpaPort.saveAndFlush(sinceDebut);
    }

    @Override
    public void updateSinceDebutForQualifying(SinceDebut sinceDebut, int position) {
        if(position == 1) sinceDebutDomainService.updatePolePosition(sinceDebut);
        sinceDebutJpaPort.saveAndFlush(sinceDebut);
    }
}
