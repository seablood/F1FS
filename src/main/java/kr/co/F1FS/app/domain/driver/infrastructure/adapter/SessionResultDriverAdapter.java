package kr.co.F1FS.app.domain.driver.infrastructure.adapter;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.infrastructure.repository.DriverRepository;
import kr.co.F1FS.app.domain.sessionresult.application.port.out.SessionResultDriverPort;
import kr.co.F1FS.app.global.util.exception.driver.DriverException;
import kr.co.F1FS.app.global.util.exception.driver.DriverExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionResultDriverAdapter implements SessionResultDriverPort {
    private final DriverRepository driverRepository;

    @Override
    public Driver getDriverByNameNotDTO(String name) {
        return driverRepository.findByName(name)
                .orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_NOT_FOUND));
    }
}
