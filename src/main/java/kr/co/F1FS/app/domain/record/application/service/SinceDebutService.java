package kr.co.F1FS.app.domain.record.application.service;

import kr.co.F1FS.app.domain.record.application.mapper.RecordMapper;
import kr.co.F1FS.app.domain.record.application.port.in.SinceDebutUseCase;
import kr.co.F1FS.app.domain.record.application.port.out.SinceDebutJpaPort;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import kr.co.F1FS.app.domain.record.presentation.dto.CreateSinceDebutDTO;
import kr.co.F1FS.app.global.presentation.dto.record.ResponseSinceDebutDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SinceDebutService implements SinceDebutUseCase {
    private final SinceDebutJpaPort sinceDebutJpaPort;
    private final RecordMapper recordMapper;

    @Override
    public SinceDebut save(SinceDebut sinceDebut) {
        return sinceDebutJpaPort.save(sinceDebut);
    }

    @Override
    public SinceDebut saveAndFlush(SinceDebut sinceDebut) {
        return sinceDebutJpaPort.saveAndFlush(sinceDebut);
    }

    @Override
    public void updateSinceDebutForRace(SinceDebut sinceDebut, int position, boolean isFastestLap) {
        sinceDebut.updateEnteredGP();
        if(position <= 3){
            sinceDebut.updatePodiums();
            if(position == 1) sinceDebut.updateHighestFinish();
        }
        if(isFastestLap) sinceDebut.updateFastestLap();

        sinceDebutJpaPort.saveAndFlush(sinceDebut);
    }

    @Override
    public void updateSinceDebutForQualifying(SinceDebut sinceDebut, int position) {
        if(position == 1) sinceDebut.updatePolePosition();
        sinceDebutJpaPort.saveAndFlush(sinceDebut);
    }

    @Override
    public SinceDebut toSinceDebut(CreateSinceDebutDTO dto) {
        return recordMapper.toSinceDebut(dto);
    }

    @Override
    public ResponseSinceDebutDTO toResponseSinceDebutDTO(SinceDebut sinceDebut) {
        return recordMapper.toResponseSinceDebutDTO(sinceDebut);
    }
}
