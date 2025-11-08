package kr.co.F1FS.app.domain.elastic.infrastructure.adapter;

import kr.co.F1FS.app.domain.admin.driver.application.port.out.AdminDriverCDSearchPort;
import kr.co.F1FS.app.domain.elastic.domain.DriverDocument;
import kr.co.F1FS.app.domain.elastic.infrastructure.repository.DriverSearchRepository;
import kr.co.F1FS.app.global.util.exception.driver.DriverException;
import kr.co.F1FS.app.global.util.exception.driver.DriverExceptionType;
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

    @Override
    public DriverDocument getDriverDocument(Long id) {
        return driverSearchRepository.findById(id)
                .orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_NOT_FOUND));
    }
}
