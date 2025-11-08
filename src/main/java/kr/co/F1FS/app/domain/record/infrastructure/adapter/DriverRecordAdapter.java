package kr.co.F1FS.app.domain.record.infrastructure.adapter;

import kr.co.F1FS.app.domain.driver.application.port.out.DriverRecordPort;
import kr.co.F1FS.app.domain.record.application.mapper.RecordMapper;
import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import kr.co.F1FS.app.domain.record.infrastructure.repository.CurrentSeasonRepository;
import kr.co.F1FS.app.domain.record.infrastructure.repository.SinceDebutRepository;
import kr.co.F1FS.app.domain.record.presentation.dto.CreateCurrentSeasonDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.CreateSinceDebutDTO;
import kr.co.F1FS.app.global.presentation.dto.record.ResponseCurrentSeasonDTO;
import kr.co.F1FS.app.global.presentation.dto.record.ResponseSinceDebutDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DriverRecordAdapter implements DriverRecordPort {
    private final CurrentSeasonRepository currentSeasonRepository;
    private final SinceDebutRepository sinceDebutRepository;
    private final RecordMapper recordMapper;

    @Override
    public void save(CurrentSeason currentSeason, SinceDebut sinceDebut) {
        currentSeasonRepository.save(currentSeason);
        sinceDebutRepository.save(sinceDebut);
    }

    @Override
    public void saveAndFlush(CurrentSeason currentSeason) {
        currentSeasonRepository.saveAndFlush(currentSeason);
    }

    @Override
    public CurrentSeason toCurrentSeason(CreateCurrentSeasonDTO dto) {
        return recordMapper.toCurrentSeason(dto);
    }

    @Override
    public SinceDebut toSinceDebut(CreateSinceDebutDTO dto) {
        return recordMapper.toSinceDebut(dto);
    }

    @Override
    public ResponseCurrentSeasonDTO toResponseCurrentSeasonDTO(CurrentSeason currentSeason) {
        return recordMapper.toResponseCurrentSeasonDTO(currentSeason);
    }

    @Override
    public ResponseSinceDebutDTO toResponseSinceDebutDTO(SinceDebut sinceDebut) {
        return recordMapper.toResponseSinceDebutDTO(sinceDebut);
    }
}
