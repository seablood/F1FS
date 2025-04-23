package kr.co.F1FS.app.application.driver;

import kr.co.F1FS.app.domain.model.elastic.DriverDocument;
import kr.co.F1FS.app.domain.model.rdb.Driver;
import kr.co.F1FS.app.domain.repository.elastic.DriverSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DriverSearchService {
    private final DriverSearchRepository driverSearchRepository;

    public void save(Driver driver){
        DriverDocument driverDocument = DriverDocument.builder()
                .driver(driver).build();

        driverSearchRepository.save(driverDocument);
    }
}
