package kr.co.F1FS.app.domain.elastic.infrastructure.adapter;

import kr.co.F1FS.app.domain.admin.driver.application.port.out.AdminDriverCDSearchPort;
import kr.co.F1FS.app.domain.elastic.domain.DriverDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.DriverSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminDriverCDSearchAdapter implements AdminDriverCDSearchPort {
    private final DriverSearchRepository driverSearchRepository;

    @Override
    public void save(DriverDocument driverDocument) {
        driverSearchRepository.save(driverDocument);
    }
}
