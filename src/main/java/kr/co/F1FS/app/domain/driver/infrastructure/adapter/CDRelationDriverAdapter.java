package kr.co.F1FS.app.domain.driver.infrastructure.adapter;

import jakarta.transaction.Transactional;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.infrastructure.repository.DriverRepository;
import kr.co.F1FS.app.domain.team.application.port.out.CDRelationDriverPort;
import kr.co.F1FS.app.global.util.exception.driver.DriverException;
import kr.co.F1FS.app.global.util.exception.driver.DriverExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CDRelationDriverAdapter implements CDRelationDriverPort {
    private final DriverRepository driverRepository;

    @Override
    public Driver findByNumber(Integer number) {
        return driverRepository.findByNumber(number)
                .orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_NOT_FOUND));
    }

    @Override
    @Transactional
    public void saveAndFlush(Driver driver) {
        driverRepository.saveAndFlush(driver);
    }

    @Override
    @Cacheable(value = "Driver", key = "#id", cacheManager = "redisLongCacheManager")
    public Driver findById(Long id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_NOT_FOUND));
    }
}
