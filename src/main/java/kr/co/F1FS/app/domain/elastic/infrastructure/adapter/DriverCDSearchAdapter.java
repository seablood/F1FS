package kr.co.F1FS.app.domain.elastic.infrastructure.adapter;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.application.port.out.DriverCDSearchPort;
import kr.co.F1FS.app.domain.elastic.application.service.CDSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DriverCDSearchAdapter implements DriverCDSearchPort {
    private final CDSearchService cdSearchService;

    @Override
    public void save(Driver driver) {
        cdSearchService.save(driver);
    }
}
