package kr.co.F1FS.app.domain.constructor.application.port.in;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.constructor.presentation.dto.CreateConstructorDTO;
import kr.co.F1FS.app.global.presentation.dto.constructor.ResponseConstructorDTO;
import kr.co.F1FS.app.global.presentation.dto.constructor.SimpleResponseConstructorDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.CreateCurrentSeasonDTO;
import kr.co.F1FS.app.domain.record.presentation.dto.CreateSinceDebutDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ConstructorUseCase {
    Constructor save(CreateConstructorDTO constructorDTO, CreateCurrentSeasonDTO currentSeasonDTO,
                     CreateSinceDebutDTO sinceDebutDTO);
    Page<SimpleResponseConstructorDTO> findAll(int page, int size, String condition);
    ResponseConstructorDTO findById(Long id);
    Constructor findByIdNotDTO(Long id);
    void updateRecordForRace(Constructor constructor, int position, int points, boolean isFastestLap);
    void updateRecordForQualifying(Constructor constructor, int position);
    void increaseFollower(Constructor constructor);
    void decreaseFollower(Constructor constructor);
    List<String> getDrivers(Constructor constructor);
    Pageable switchCondition(int page, int size, String condition);
}
