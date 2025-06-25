package kr.co.F1FS.app.domain.elastic.application.port.in;

import kr.co.F1FS.app.domain.constructor.domain.Constructor;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.elastic.domain.ConstructorDocument;
import kr.co.F1FS.app.domain.elastic.domain.DriverDocument;
import kr.co.F1FS.app.domain.elastic.presentation.dto.CDSearchSuggestionDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CDSearchUseCase {
    ConstructorDocument save(Constructor constructor);
    DriverDocument save(Driver driver);
    List<CDSearchSuggestionDTO> suggestCD(String keyword);
    Page<CDSearchSuggestionDTO> searchCDWithPaging(int page, int size, String condition, String keyword);
}
