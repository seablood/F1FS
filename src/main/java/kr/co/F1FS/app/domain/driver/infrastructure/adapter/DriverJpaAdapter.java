package kr.co.F1FS.app.domain.driver.infrastructure.adapter;

import kr.co.F1FS.app.domain.driver.application.mapper.DriverMapper;
import kr.co.F1FS.app.domain.driver.application.port.out.DriverJpaPort;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.domain.driver.infrastructure.repository.DriverRepository;
import kr.co.F1FS.app.global.presentation.dto.driver.SimpleResponseDriverDTO;
import kr.co.F1FS.app.global.util.exception.driver.DriverException;
import kr.co.F1FS.app.global.util.exception.driver.DriverExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DriverJpaAdapter implements DriverJpaPort {
    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    @Override
    public Driver save(Driver driver) {
        return driverRepository.save(driver);
    }

    @Override
    public Driver saveAndFlush(Driver driver) {
        return driverRepository.saveAndFlush(driver);
    }

    @Override
    public Page<SimpleResponseDriverDTO> findAll(Pageable pageable) {
        return driverRepository.findAll(pageable).map(driver -> driverMapper.toSimpleResponseDriverDTO(driver));
    }

    @Override
    public Driver findById(Long id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_NOT_FOUND));
    }

    @Override
    public Driver findByName(String name) {
        return driverRepository.findByName(name)
                .orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_NOT_FOUND));
    }

    @Override
    public Driver findByEngName(String engName) {
        return driverRepository.findByEngName(engName)
                .orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_NOT_FOUND));
    }

    @Override
    public Driver findByNumber(Integer number) {
        return driverRepository.findByNumber(number)
                .orElseThrow(() -> new DriverException(DriverExceptionType.DRIVER_NOT_FOUND));
    }
}
