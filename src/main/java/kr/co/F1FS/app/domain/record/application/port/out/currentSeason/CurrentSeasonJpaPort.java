package kr.co.F1FS.app.domain.record.application.port.out.currentSeason;

import kr.co.F1FS.app.domain.record.domain.CurrentSeason;

public interface CurrentSeasonJpaPort {
    CurrentSeason save(CurrentSeason currentSeason);
    CurrentSeason saveAndFlush(CurrentSeason currentSeason);
}
