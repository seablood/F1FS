package kr.co.F1FS.app.domain.driver.infrastructure.adapter;

import kr.co.F1FS.app.domain.admin.driver.application.port.out.AdminDriverPort;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.infrastructure.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminDriverAdapter implements AdminDriverPort {
    private final DriverRepository driverRepository;

    @Override
    public Driver save(Driver driver) {
        return driverRepository.save(driver);
    }
}
