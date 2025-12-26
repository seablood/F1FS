package kr.co.F1FS.app.domain.record.application.service.sinceDebut;

import kr.co.F1FS.app.domain.record.application.mapper.RecordMapper;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import kr.co.F1FS.app.domain.record.presentation.dto.sinceDebut.CreateSinceDebutDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SinceDebutDomainService {
    private final RecordMapper recordMapper;

    public SinceDebut createEntity(CreateSinceDebutDTO sinceDebutDTO){
        return recordMapper.toSinceDebut(sinceDebutDTO);
    }

    public SinceDebut createEntity(){
        return new SinceDebut();
    }

    public void updateEnteredGP(SinceDebut sinceDebut){
        sinceDebut.updateEnteredGP();
    }

    public void updatePodiums(SinceDebut sinceDebut){
        sinceDebut.updatePodiums();
    }

    public void updateHighestFinish(SinceDebut sinceDebut){
        sinceDebut.updateHighestFinish();
    }

    public void updateFastestLap(SinceDebut sinceDebut){
        sinceDebut.updateFastestLap();
    }

    public void updatePolePosition(SinceDebut sinceDebut){
        sinceDebut.updatePolePosition();
    }
}
