package kr.co.F1FS.app.domain.driver.application.service.driver;

import kr.co.F1FS.app.domain.driver.application.mapper.driver.DriverMapper;
import kr.co.F1FS.app.domain.driver.application.port.in.driver.QueryDriverUseCase;
import kr.co.F1FS.app.domain.driver.application.port.out.driver.DriverJpaPort;
import kr.co.F1FS.app.domain.driver.domain.rdb.Driver;
import kr.co.F1FS.app.global.presentation.dto.driver.SimpleResponseDriverDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryDriverService implements QueryDriverUseCase {
    private final DriverJpaPort driverJpaPort;
    private final DriverMapper driverMapper;

    @Override
    public Driver findById(Long id) {
        return driverJpaPort.findById(id);
    }

    @Override
    public Driver findByName(String name) {
        return driverJpaPort.findByName(name);
    }

    @Override
    public Driver findByNumber(Integer number) {
        return driverJpaPort.findByNumber(number);
    }

    @Override
    public Page<SimpleResponseDriverDTO> findAllForSimpleDTO(Pageable pageable) {
        return driverJpaPort.findAll(pageable)
                .map(driver -> driverMapper.toSimpleResponseDriverDTO(driver));
    }
}
