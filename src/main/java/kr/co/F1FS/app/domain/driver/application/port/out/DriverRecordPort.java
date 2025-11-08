package kr.co.F1FS.app.domain.driver.application.port.out;

import kr.co.F1FS.app.domain.record.domain.CurrentSeason;
import kr.co.F1FS.app.domain.record.domain.SinceDebut;
import kr.co.F1FS.app.domain.record.presentation.dto.CreateCurrentSeasonDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.CreateSinceDebutDTO;
import kr.co.F1FS.app.global.presentation.dto.record.ResponseCurrentSeasonDTO;
import kr.co.F1FS.app.global.presentation.dto.record.ResponseSinceDebutDTO;

public interface DriverRecordPort {
    void save(CurrentSeason currentSeason, SinceDebut sinceDebut);
    void saveAndFlush(CurrentSeason currentSeason);
    CurrentSeason toCurrentSeason(CreateCurrentSeasonDTO dto);
    SinceDebut toSinceDebut(CreateSinceDebutDTO dto);
    ResponseCurrentSeasonDTO toResponseCurrentSeasonDTO(CurrentSeason currentSeason);
    ResponseSinceDebutDTO toResponseSinceDebutDTO(SinceDebut sinceDebut);
}
