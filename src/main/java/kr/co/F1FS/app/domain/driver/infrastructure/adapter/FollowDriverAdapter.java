package kr.co.F1FS.app.domain.driver.infrastructure.adapter;

import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.infrastructure.repository.DriverRepository;
import kr.co.F1FS.app.domain.follow.application.port.out.FollowDriverPort;
import kr.co.F1FS.app.global.util.exception.driver.DriverException;
import kr.co.F1FS.app.global.util.exception.driver.DriverExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FollowDriverAdapter implements FollowDriverPort {
    private final DriverRepository driverRepository;

    @Override
    public Driver findByIdNotDTO(Long id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_NOT_FOUND));
    }

    @Override
    public void saveAndFlush(Driver driver) {
        driverRepository.saveAndFlush(driver);
    }
}
